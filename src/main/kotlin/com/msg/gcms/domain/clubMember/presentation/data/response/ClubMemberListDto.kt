package com.msg.gcms.domain.clubMember.presentation.data.response

import com.msg.gcms.domain.clubMember.enums.MemberScope
import java.util.*

data class ClubMemberListDto(
    val scope: MemberScope,
    val clubMember: List<SingleClubMemberDto>
) {
    data class SingleClubMemberDto(
        val uuid: UUID,
        val email: String,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?,
        val scope: MemberScope
    )
}