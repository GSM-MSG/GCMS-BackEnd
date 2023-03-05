package com.msg.gcms.domain.user.presentaion.data.response

import com.msg.gcms.domain.auth.domain.Role

data class UserProfileResponseDto (
    val name: String,
    val profileImg: String?,
    val role: Role
)