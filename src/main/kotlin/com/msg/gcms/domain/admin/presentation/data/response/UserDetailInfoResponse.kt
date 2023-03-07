package com.msg.gcms.domain.admin.presentation.data.response

import com.msg.gcms.domain.admin.presentation.data.dto.ClubInfoDto

data class UserDetailInfoResponse (
        val nickname: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?,
        val clubs: List<ClubInfoDto>
)
