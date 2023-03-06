package com.msg.gcms.domain.admin.presentation.data.response

import java.util.UUID

data class FindAllUserListResponse (
        val uuid: UUID,
        val nickname: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?,
)