package com.msg.gcms.domain.user.presentaion.data.dto

data class SearchUserDto (
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?
)