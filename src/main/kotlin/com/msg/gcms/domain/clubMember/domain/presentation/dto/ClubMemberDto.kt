package com.msg.gcms.domain.clubMember.domain.presentation.dto

import com.msg.gcms.domain.clubMember.enums.MemberScope
import java.util.UUID

class ClubMemberDto(
    private val uuid: UUID,
    private val email: String,
    private val name: String,
    private val grade: Int,
    private val classNum: Int,
    private val number: Int,
    private val profileImg: String,
    private val scope: MemberScope
) {

}