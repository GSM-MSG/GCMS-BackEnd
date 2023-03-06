package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.admin.presentation.data.dto.UserDetailInfoDto
import com.msg.gcms.domain.admin.presentation.data.request.UserDetailInfoRequest

interface UserDetailInfoService {
    fun execute(userDetailInfoRequest: UserDetailInfoRequest): UserDetailInfoDto
}