package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.admin.domain.repository.AdminRepository
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.auth.exception.RoleNotExistException
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.AuthUtil
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.gauth.properties.GAuthProperties
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
    private val authUtil: AuthUtil,
    private val adminRepository: AdminRepository
) : SignInService {


    override fun execute(signInDto: SignInDto): SignInResponseDto {
        val gAuthToken: GAuthToken = gAuth.generateToken(
            signInDto.code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        )
        val gAuthUserInfo: GAuthUserInfo = gAuth.getUserInfo(gAuthToken.accessToken)
        val role = getRoleByGauthInfo(gAuthUserInfo.role)

        val accessToken: String = jwtTokenProvider.generateAccessToken(gAuthUserInfo.email, role)
        val refreshToken: String = jwtTokenProvider.generateRefreshToken(gAuthUserInfo.email, role)
        val accessExp: ZonedDateTime = jwtTokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = jwtTokenProvider.refreshExpiredTime

        if(role == Role.ROLE_ADMIN) {
            createAdminOrRefreshToken(gAuthUserInfo, refreshToken)
        } else {
            createUserOrRefreshToken(gAuthUserInfo, refreshToken)
        }

        return SignInResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
    }

    private fun getRoleByGauthInfo(role: String): Role {
        return when (role) {
            "ROLE_STUDENT" -> Role.ROLE_STUDENT
            "ROLE_TEACHER" -> Role.ROLE_ADMIN
            else -> throw RoleNotExistException()
        }
    }

    private fun createUserOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val userInfo = userRepository.findByEmail(gAuthUserInfo.email)
        if (userInfo == null) {
            authUtil.saveNewUser(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(userInfo, refreshToken)
        }
    }

    private fun createAdminOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val adminInfo = adminRepository.findByEmail(gAuthUserInfo.email)
        if (adminInfo == null) {
            authUtil.saveNewAdmin(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(adminInfo, refreshToken)
        }
    }

}