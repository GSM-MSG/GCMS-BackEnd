package com.msg.gcms.domain.user.presentaion.data.response

import java.util.UUID

data class SearchUserResponseDto (
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?
)