package com.msg.gcms.domain.admin.presentation.data.dto

import java.util.UUID

data class FindAllUserListDto (
        val uuid: UUID,
        val nickname: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?,
)