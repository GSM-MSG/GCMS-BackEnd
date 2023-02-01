package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.user.presentaion.data.dto.UserDto

interface FindUserService {
    fun execute(): UserDto
}