package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.AuthUtil
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
    private val gAuth: GAuth,
    private val authUtil: AuthUtil
) : SignInService {


    override fun execute(signInDto: SignInDto): SignInResponseDto {
        val gAuthToken: GAuthToken = gAuth.generateToken(
            signInDto.code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        )
        val gAuthUserInfo: GAuthUserInfo = gAuth.getUserInfo(gAuthToken.accessToken)

        val userInfo = userRepository.findByEmail(gAuthUserInfo.email)

        val accessToken: String = jwtTokenProvider.generateAccessToken(gAuthUserInfo.email)
        val refreshToken: String = jwtTokenProvider.generateRefreshToken(gAuthUserInfo.email)
        val accessExp: ZonedDateTime = jwtTokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = jwtTokenProvider.refreshExpiredTime

        if (userInfo == null) {
            authUtil.saveNewUser(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(userInfo, refreshToken)
        }

        return SignInResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
    }

}