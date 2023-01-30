package com.msg.gcms.domain.clubMember.presentation.data.dto

import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope

data class ClubMemberListDto(
    val scope: MemberScope,
    val clubMember: List<ClubMemberDto>
)