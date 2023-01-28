package com.msg.gcms.domain.clubMember.domain.presentation.dto

import com.msg.gcms.domain.clubMember.enums.MemberScope

class ClubMemberListDto(
    val scope: MemberScope,
    val clubMember: List<ClubMemberDto>
) {
}