package com.msg.gcms.domain.clubMember.domain.presentation.dto.response

import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.enums.MemberScope

class ClubMemberListResponse(
    val scope: MemberScope,
    val clubMember: List<ClubMemberListDto>
) {
}