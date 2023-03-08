package com.msg.gcms.domain.auth.util.impl

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.AuthUtil
import com.msg.gcms.domain.user.domain.entity.DeviceToken
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.DeviceTokenRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component

@Component
class AuthUtilImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authConverter: AuthConverter,
    private val userRepository: UserRepository,
    private val deviceTokenRepository: DeviceTokenRepository
) : AuthUtil {

    override fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String?) {
        val signInUserInfo: User = authConverter.toEntity(gAuthUserInfo)
            .let { userRepository.save(it) }
        saveNewRefreshToken(signInUserInfo, refreshToken, token)
    }

    override fun saveNewAdmin(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String?) {
        val signInAdminInfo: User = authConverter.toAdminEntity(gAuthUserInfo)
            .let { userRepository.save(it) }
        saveNewRefreshToken(signInAdminInfo, refreshToken, token)
    }

    override fun saveNewRefreshToken(userInfo: User, refreshToken: String, token: String?): RefreshToken {
        deviceTokenRepository.save(DeviceToken(userInfo.id, userInfo, token ?: ""))
        return authConverter.toEntity(userInfo, refreshToken)
            .let { refreshTokenRepository.save(it) }
    }
}