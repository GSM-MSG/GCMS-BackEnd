package com.msg.gcms.domain.auth.service


import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.service.impl.SignInServiceImpl
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.impl.AuthUtilImpl
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.DeviceTokenRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.gauth.properties.GAuthProperties
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class SignInServiceTest : BehaviorSpec({
    val clientId = "thisIsClientId"
    val clientSecret = "thisIsClientSecret"
    val redirectUri = "thisIsRedirectUri"

    val tokenProvider = mockk<JwtTokenProvider>(relaxed = true)
    val gAuth = mockk<GAuth>()
    val userRepository = mockk<UserRepository>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val authConverter = mockk<AuthConverter>()
    val deviceTokenRepository = mockk<DeviceTokenRepository>()
    val gAuthProperties = GAuthProperties(clientId = clientId, clientSecret = clientSecret, redirectUri = redirectUri)
    val authUtil = AuthUtilImpl(
        refreshTokenRepository = refreshTokenRepository,
        authConverter = authConverter,
        userRepository = userRepository,
        deviceTokenRepository = deviceTokenRepository
    )

    val signInService = SignInServiceImpl(
        gAuthProperties = gAuthProperties,
        userRepository = userRepository,
        jwtTokenProvider = tokenProvider,
        gAuth = gAuth,
        authUtil = authUtil,
    )

    given("gAuth로 로그인 요청이 갔을때") {
        val code = "thisIsCode"

        val map: Map<String, String> = mapOf(
            "accessToken" to "thisIsAccessToken",
            "refreshToken" to "thisIsRefreshToken"
        )


        val gAuthToken = GAuthToken(map)
        every {
            gAuth.generateToken(
                code,
                gAuthProperties.clientId,
                gAuthProperties.clientSecret,
                gAuthProperties.redirectUri
            )
        } returns gAuthToken

        val accessToken = "thisIsAccessToken"
        val refreshToken = "thisIsRefreshToken"

        val role = Role.ROLE_STUDENT
        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, mutableListOf(role), null, listOf(), listOf(), listOf())

        val userMap: Map<String, Any> = mapOf(
            "email" to user.email,
            "name" to "김시훈",
            "grade" to 2,
            "classNum" to 1,
            "num" to 4,
            "gender" to "MALE",
            "profileUrl" to "https://velog.velcdn.com/images/msung99/post/9af546d4-210c-4647-9ccf-2f2878f0283b/image.png",
            "role" to "ROLE_STUDENT"
        )

        val gAuthUserInfo = GAuthUserInfo(userMap)

        every {
            gAuth.getUserInfo(
                gAuthToken.accessToken
            )
        } returns gAuthUserInfo

        val refreshTokenEntity = RefreshToken(
            userId = user.id,
            token = refreshToken
        )

        every {
            authUtil.saveRefreshToken(userInfo = user, refreshToken = refreshToken, "")
        } returns refreshTokenEntity

        val signInDto = SignInDto(
            code = "thisIsCode",
            token = ""
        )

        every {
            tokenProvider.generateAccessToken(gAuthUserInfo.email, role)
        } returns accessToken

        every {
            tokenProvider.generateRefreshToken(gAuthUserInfo.email, role)
        } returns refreshToken

        val accessExp = tokenProvider.accessExpiredTime
        val refreshExp = tokenProvider.refreshExpiredTime

        every {
            tokenProvider.accessExpiredTime
        } returns accessExp

        every {
            tokenProvider.refreshExpiredTime
        } returns refreshExp

        every {
            authConverter.toEntity(user, refreshToken)
        } returns refreshTokenEntity

        every {
            userRepository.save(user)
        } returns user

        userRepository.save(user)

        every {
            refreshTokenRepository.save(refreshTokenEntity)
        } returns refreshTokenEntity

        refreshTokenRepository.save(refreshTokenEntity)

        `when`("signInDto가 주어지고 유저가 첫 로그인 시도 일때") {

            every {
                userRepository.findByEmail(gAuthUserInfo.email)
            } returns null

            then("user insert 쿼리가 실행되어야 함") {
                verify(exactly = 1) { userRepository.save(user) }
            }

            then("refreshToken insert 쿼리가 실행되어야 함") {
                verify(exactly = 1) { refreshTokenRepository.save(refreshTokenEntity) }
            }

            val result = signInService.execute(signInDto)
            then("토큰 값 비교") {
                result.accessToken shouldBe accessToken
                result.refreshToken shouldBe refreshToken
                result.accessExp shouldBe accessExp
                result.refreshExp shouldBe refreshExp
            }

        }


        `when`("signInDto가 주어지고 유저가 로그인 했던 유저라면") {

            every {
                userRepository.findByEmail(gAuthUserInfo.email)
            } returns user

            then("user insert 쿼리가 실행되지 않는다") {
                verify { userRepository.save(user) wasNot Called }
            }

            then("refreshToken update 쿼리가 실행되어야 함") {
                verify(exactly = 2) { refreshTokenRepository.save(refreshTokenEntity) }
            }

            val result = signInService.execute(signInDto)
            then("토큰 값 비교") {
                result.accessToken shouldBe accessToken
                result.refreshToken shouldBe refreshToken
                result.accessExp shouldBe accessExp
                result.refreshExp shouldBe refreshExp
            }
        }

    }

})