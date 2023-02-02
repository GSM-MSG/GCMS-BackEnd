package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto

interface SearchUserService {
    fun execute(): SearchUserDto
}