package com.msg.gcms.domain.user.presentaion.data.response

data class SearchUserResponseDto (
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?
)