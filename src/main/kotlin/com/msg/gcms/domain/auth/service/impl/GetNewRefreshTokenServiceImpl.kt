package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.exception.ExpiredRefreshTokenException
import com.msg.gcms.domain.auth.presentation.data.response.NewRefreshTokenResponseDto
import com.msg.gcms.domain.auth.service.GetNewRefreshTokenService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.global.security.exception.InvalidTokenException
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class GetNewRefreshTokenServiceImpl(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authConverter: AuthConverter
) : GetNewRefreshTokenService {

    override fun execute(refreshToken: String): NewRefreshTokenResponseDto {
        val refresh = jwtTokenProvider.parseToken(refreshToken)
            ?: throw InvalidTokenException()
        val email: String = jwtTokenProvider.exactEmailFromRefreshToken(refresh)
        val existingRefreshToken = refreshTokenRepository.findByToken(refresh)
            ?: throw ExpiredRefreshTokenException()
        val newAccessToken = jwtTokenProvider.generateAccessToken(email)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(email)
        val accessExp: ZonedDateTime = jwtTokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = jwtTokenProvider.refreshExpiredTime

        val newRefreshTokenEntity = authConverter.toEntity(
            userId = existingRefreshToken.userId,
            refreshToken = newRefreshToken
        )
        refreshTokenRepository.save(newRefreshTokenEntity)

        return NewRefreshTokenResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
    }

}