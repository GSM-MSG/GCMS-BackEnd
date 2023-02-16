package com.msg.gcms.domain.auth.service.impl

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.service.LogoutService
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class LogoutServiceImpl(
    private val userUtil: UserUtil,
    private val refreshTokenRepository: RefreshTokenRepository
) : LogoutService {
    override fun execute() {

        val userInfo: User = userUtil.fetchCurrentUser()

        val refreshToken: RefreshToken = refreshTokenRepository.findByUserId(userInfo.id)
            ?: throw UserNotFoundException()

        refreshTokenRepository.delete(refreshToken)
    }

}