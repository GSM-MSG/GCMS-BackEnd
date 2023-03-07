package com.msg.gcms.domain.admin.presentation.data.dto

data class UserDetailInfoDto (
        val nickname: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?,
        val clubs: List<ClubInfoDto>
)