package com.msg.gcms.domain.clubMember.presentation.data.response

import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope

class ClubMemberListResponse(
    val scope: MemberScope,
    val clubMember: List<ClubMemberDto>
) {
}