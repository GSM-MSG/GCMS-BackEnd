package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.auth.exception.RoleNotExistException
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthUtil
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.gauth.properties.GAuthProperties
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import java.time.ZonedDateTime


@ServiceWithTransaction
class SignInServiceImpl(
    private val gAuthProperties: GAuthProperties,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val gAuth: GAuth,
    private val authUtil: AuthUtil,
) : SignInService {

    override fun execute(signInDto: SignInDto): SignInResponseDto {
        val gAuthToken: GAuthToken = gAuth.generateToken(
            signInDto.code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        )
        val gAuthUserInfo: GAuthUserInfo = gAuth.getUserInfo(gAuthToken.accessToken)
        val role = getRoleByGAuthInfo(gAuthUserInfo.role, gAuthUserInfo.email)
        val token = signInDto.token

        val accessToken: String = jwtTokenProvider.generateAccessToken(gAuthUserInfo.email, role)
        val refreshToken: String = jwtTokenProvider.generateRefreshToken(gAuthUserInfo.email, role)
        val accessExp: ZonedDateTime = jwtTokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = jwtTokenProvider.refreshExpiredTime

        createUserByRoleOrRefreshToken(gAuthUserInfo, refreshToken, token, role)

        return SignInResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
    }

    private fun getRoleByGAuthInfo(role: String, email: String): Role {
        val user = userRepository.findByEmail(email) ?:
        return when (role) {
            "ROLE_STUDENT" -> Role.ROLE_STUDENT
            "ROLE_TEACHER" -> Role.ROLE_ADMIN
            else -> throw RoleNotExistException()
        }
        if(user.roles.contains(Role.ROLE_ADMIN))
            return Role.ROLE_ADMIN
        return Role.ROLE_STUDENT
    }

    private fun createUserByRoleOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String?, role: Role) {
        val userInfo = userRepository.findByEmail(gAuthUserInfo.email)
            ?: authUtil.saveNewUser(gAuthUserInfo, refreshToken, token, role)

        authUtil.saveRefreshToken(userInfo, refreshToken, token)
    }
}