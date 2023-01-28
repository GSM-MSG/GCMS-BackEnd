package com.msg.gcms.domain.clubMember.domain.presentation.dto

import com.msg.gcms.domain.clubMember.enums.MemberScope
import java.util.UUID

class ClubMemberDto(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?,
    val scope: MemberScope
) {

}