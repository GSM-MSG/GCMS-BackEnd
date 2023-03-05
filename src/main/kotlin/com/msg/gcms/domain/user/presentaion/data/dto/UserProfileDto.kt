package com.msg.gcms.domain.user.presentaion.data.dto

import com.msg.gcms.domain.auth.domain.Role

data class UserProfileDto(
    val name: String,
    val profileImg: String?,
    val role: Role
)
