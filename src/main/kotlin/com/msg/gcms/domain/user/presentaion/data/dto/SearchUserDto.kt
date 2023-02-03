package com.msg.gcms.domain.user.presentaion.data.dto

import java.util.UUID

data class SearchUserDto (
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?
)