package com.msg.gcms.domain.clubMember.domain.util

import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.enums.MemberScope

interface ClubMemberConverter {
    fun toDto(entity: ClubMember): ClubMemberDto
    fun toListDto(scope: MemberScope, dto: List<ClubMemberDto>): ClubMemberListDto
}