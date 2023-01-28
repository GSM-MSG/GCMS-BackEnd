package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.gAuth.properties.GAuthProperties
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SignInServiceImpl(
    private val gAuthProperties: GAuthProperties,
    private val userRepository: UserRepository,
    private val authConverter: AuthConverter,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) : SignInService {

    override fun execute(signInDto: SignInDto): SignInResponseDto {
        val gAuthToken: GAuthToken = GAuth.generateToken(
            signInDto.code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        )
        val gAuthUserInfo: GAuthUserInfo = GAuth.getUserInfo(gAuthToken.accessToken)

        val userInfo = userRepository.findByEmail(gAuthUserInfo.email)

        val accessToken = jwtTokenProvider.generateAccessToken(gAuthUserInfo.email)
        val refreshToken: String = jwtTokenProvider.generateRefreshToken(gAuthUserInfo.email)
        val accessExp: ZonedDateTime = jwtTokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = jwtTokenProvider.refreshExpiredTime

        if (userInfo == null) {
            val signInUserInfo: User = authConverter.toEntity(gAuthUserInfo)
                .let { userRepository.save(it) }
            authConverter.toEntity(signInUserInfo, refreshToken)
                .let { refreshTokenRepository.save(it) }
        } else {
            val newRefreshToken: RefreshToken = authConverter.toEntity(userInfo, refreshToken)
            refreshTokenRepository.save(newRefreshToken)
        }

        return authConverter.toResponse(
            accessToken,
            refreshToken,
            accessExp,
            refreshExp
        )
    }

}