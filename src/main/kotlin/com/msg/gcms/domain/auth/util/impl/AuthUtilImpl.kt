package com.msg.gcms.domain.auth.util.impl

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.AuthUtil
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component

@Component
class AuthUtilImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authConverter: AuthConverter,
    private val userRepository: UserRepository,
) : AuthUtil {

    override fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val signInUserInfo: User = authConverter.toEntity(gAuthUserInfo)
            .let { userRepository.save(it) }
        authConverter.toEntity(signInUserInfo, refreshToken)
            .let { refreshTokenRepository.save(it) }
    }

    override fun saveNewAdmin(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val signInAdminInfo: User = authConverter.toAdminEntity(gAuthUserInfo)
            .let { userRepository.save(it) }
        saveNewRefreshToken(signInAdminInfo, refreshToken)
    }

    override fun saveNewRefreshToken(userInfo: User, refreshToken: String): RefreshToken =
        authConverter.toEntity(userInfo, refreshToken)
            .let { refreshTokenRepository.save(it) }
}