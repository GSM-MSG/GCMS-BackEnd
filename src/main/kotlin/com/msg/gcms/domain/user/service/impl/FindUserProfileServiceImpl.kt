package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.user.presentaion.data.dto.UserProfileDto
import com.msg.gcms.domain.user.service.FindUserProfileService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindUserProfileServiceImpl(
    private val userUtil: UserUtil,
    private val userConverter: UserConverter
) : FindUserProfileService {
    override fun execute(): UserProfileDto =
        userUtil.fetchCurrentUser()
            .let { userConverter.toUserProfileDto(it) }

}