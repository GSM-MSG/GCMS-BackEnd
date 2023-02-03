package com.msg.gcms.domain.clubMember.presentation.data.response

import com.msg.gcms.domain.clubMember.enums.MemberScope

data class ClubMemberListResponse(
    val scope: MemberScope,
    val clubMember: List<ClubMemberListDto.SingleClubMemberDto>
)