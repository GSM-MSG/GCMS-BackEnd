package com.msg.gcms.domain.auth.service

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.service.impl.SignInServiceImpl
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.AuthUtil
import com.msg.gcms.domain.auth.util.impl.AuthConverterImpl
import com.msg.gcms.domain.auth.util.impl.AuthUtilImpl
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.gAuth.properties.GAuthProperties
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
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
    val gAuthProperties = GAuthProperties(clientId = clientId, clientSecret = clientSecret, redirectUri = redirectUri)
    val authUtil = AuthUtilImpl(
        refreshTokenRepository = refreshTokenRepository,
        authConverter = authConverter,
        userRepository = userRepository
    )

    val signInService = SignInServiceImpl(
        gAuthProperties = gAuthProperties,
        userRepository = userRepository,
        authConverter = authConverter,
        jwtTokenProvider = tokenProvider,
        gAuth = gAuth,
        authUtil = authUtil
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

        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, null, listOf(), listOf(), listOf())

        val userMap: Map<String, Any> = mapOf(
            "email" to user.email,
            "name" to "김시훈",
            "grade" to 2,
            "classNum" to 1,
            "num" to 4,
            "gender" to "MALE",
            "profileUrl" to "https://velog.velcdn.com/images/msung99/post/9af546d4-210c-4647-9ccf-2f2878f0283b/image.png",
            "role" to "student"
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
            authUtil.saveNewRefreshToken(userInfo = user, refreshToken = refreshToken)
        } returns refreshTokenEntity

        val signInDto = SignInDto(
            code = "thisIsCode"
        )

        every {
            tokenProvider.generateAccessToken(gAuthUserInfo.email)
        } returns accessToken

        every {
            tokenProvider.generateRefreshToken(gAuthUserInfo.email)
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
            authConverter.toEntity(gAuthUserInfo)
        } returns user

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