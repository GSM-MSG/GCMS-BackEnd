package com.msg.gcms.domain.clubMember.presentation.data.dto

import com.msg.gcms.domain.clubMember.enums.MemberScope

data class ClubMemberListDto(
    val scope: MemberScope,
    val clubMember: List<ClubMemberDto>
)