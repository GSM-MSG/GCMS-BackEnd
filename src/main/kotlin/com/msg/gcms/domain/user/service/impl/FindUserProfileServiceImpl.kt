package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.user.presentaion.data.dto.UserProfileDto
import com.msg.gcms.domain.user.service.FindUserProfileService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil

@ServiceWithReadOnlyTransaction
class FindUserProfileServiceImpl(
    private val userUtil: UserUtil,
    private val userConverter: UserConverter
) : FindUserProfileService {
    override fun execute(): UserProfileDto =
        userUtil.fetchCurrentUser()
            .let { userConverter.toUserProfileDto(it) }

}