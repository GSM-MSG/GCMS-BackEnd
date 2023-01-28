package com.msg.gcms.domain.clubMember.domain.presentation.dto

import com.msg.gcms.domain.clubMember.enums.MemberScope

class ClubMemberListDto(
    private val scope: MemberScope,
    private val clubMember: List<ClubMemberDto>
) {
}