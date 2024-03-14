package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.exception.ExpiredRefreshTokenException
import com.msg.gcms.domain.auth.presentation.data.dto.DeviceTokenDto
import com.msg.gcms.domain.auth.presentation.data.response.NewRefreshTokenResponseDto
import com.msg.gcms.domain.auth.service.GetNewRefreshTokenService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.user.domain.entity.DeviceToken
import com.msg.gcms.domain.user.domain.repository.DeviceTokenRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.security.exception.InvalidTokenException
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import java.time.ZonedDateTime

@ServiceWithTransaction
class GetNewRefreshTokenServiceImpl(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authConverter: AuthConverter,
    private val deviceTokenRepository: DeviceTokenRepository,
    private val userRepository: UserRepository,
) : GetNewRefreshTokenService {

    override fun execute(refreshToken: String, deviceTokenDto: DeviceTokenDto): NewRefreshTokenResponseDto {
        val refresh = jwtTokenProvider.parseToken(refreshToken)
            ?: throw InvalidTokenException()
        val email: String = jwtTokenProvider.exactEmailFromRefreshToken(refresh)
        val role: Role = jwtTokenProvider.exactRoleFromRefreshToken(refresh)
        val existingRefreshToken = refreshTokenRepository.findByToken(refresh)
            ?: throw ExpiredRefreshTokenException()
        val newAccessToken = jwtTokenProvider.generateAccessToken(email, role)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(email, role)
        val accessExp: ZonedDateTime = jwtTokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = jwtTokenProvider.refreshExpiredTime

        val newRefreshTokenEntity = authConverter.toEntity(
            userId = existingRefreshToken.userId,
            refreshToken = newRefreshToken
        )

        refreshTokenRepository.save(newRefreshTokenEntity)
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException()
        deviceTokenRepository.save(DeviceToken(user.id, user, deviceTokenDto.token?:""))

        return NewRefreshTokenResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
    }

}