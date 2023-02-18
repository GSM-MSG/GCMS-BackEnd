package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.user.presentaion.data.dto.UserProfileDto

interface FindUserProfileService {
    fun execute(): UserProfileDto
}