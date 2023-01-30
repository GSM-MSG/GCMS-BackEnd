package com.msg.gcms.domain.clubMember.presentation.data.dto

import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope
import java.util.UUID

data class ClubMemberDto(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?,
    val scope: MemberScope
)