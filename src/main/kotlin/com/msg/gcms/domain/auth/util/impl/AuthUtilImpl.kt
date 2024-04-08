package com.msg.gcms.domain.auth.util.impl

import com.msg.gcms.domain.auth.domain.Role
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

    override fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String, role: Role): User {
        val user = authConverter.toEntity(gAuthUserInfo, role)
            .let { userRepository.save(it) }

        return user
    }

    override fun saveRefreshToken(userInfo: User, refreshToken: String, token: String): RefreshToken {
        deviceTokenRepository.save(
            DeviceToken(
               userId = userInfo.id,
               user = userInfo, 
               token = token
           )
       )
        return authConverter.toEntity(userInfo, refreshToken)
            .let { refreshTokenRepository.save(it) }
    }
}