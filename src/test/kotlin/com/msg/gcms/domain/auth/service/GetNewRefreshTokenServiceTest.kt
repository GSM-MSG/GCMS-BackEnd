package com.msg.gcms.domain.auth.service

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.service.impl.GetNewRefreshTokenServiceImpl
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.impl.AuthConverterImpl
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.ZonedDateTime
import java.util.*

class GetNewRefreshTokenServiceTest : BehaviorSpec({
    val tokenProvider = mockk<JwtTokenProvider>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val authConverter = mockk<AuthConverter>()

    val getNewRefreshTokenService = GetNewRefreshTokenServiceImpl(
        jwtTokenProvider = tokenProvider,
        refreshTokenRepository = refreshTokenRepository,
        authConverter = authConverter
    )

    given("refresh 토큰이 주어졌을때") {
        val refreshToken = "thisIsAfterRefreshToken"

        val accessToken = "thisIsAccessToken"
        val newRefreshToken = "thisIsBeforeRefreshToken"
        val role = Role.STUDENT
        val accessExp = ZonedDateTime.now()
        val refreshExp = ZonedDateTime.now()

        every { tokenProvider.parseToken(refreshToken) } returns refreshToken

        val email = "s21024@gsm.hs.kr"

        every { tokenProvider.exactEmailFromRefreshToken(refreshToken) } returns email
        every { tokenProvider.exactRoleFromRefreshToken(refreshToken) } returns role

        val refreshTokenEntity = RefreshToken(
            userId = UUID.randomUUID(),
            token = refreshToken
        )

        every { refreshTokenRepository.findByToken(refreshToken) } returns refreshTokenEntity

        every { tokenProvider.generateAccessToken(email, role) } returns accessToken
        every { tokenProvider.generateRefreshToken(email, role) } returns newRefreshToken
        every { tokenProvider.accessExpiredTime } returns accessExp
        every { tokenProvider.refreshExpiredTime } returns refreshExp

        val newRefreshTokenEntity = RefreshToken(
            userId = refreshTokenEntity.userId,
            token = newRefreshToken
        )

        every { authConverter.toEntity(userId = refreshTokenEntity.userId, refreshToken = newRefreshToken) } returns newRefreshTokenEntity

        `when`("토큰들을 재발급 하였을때") {

            every { refreshTokenRepository.save(newRefreshTokenEntity) } returns newRefreshTokenEntity

            refreshTokenRepository.save(newRefreshTokenEntity)

            then("refreshToken update 쿼리가 실행되어야 함") {
                verify(exactly = 1) { refreshTokenRepository.save(newRefreshTokenEntity) }
            }

            val result = getNewRefreshTokenService.execute(refreshToken)
            then("토큰 값 비교") {
                result.accessToken shouldBe accessToken
                result.refreshToken shouldBe newRefreshToken
                result.accessExp shouldBe accessExp
                result.refreshExp shouldBe refreshExp
            }
        }

    }
})