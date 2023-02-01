package com.msg.gcms.domain.clubMember.util

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse

interface ClubMemberConverter {
    fun toDto(entity: ClubMember, scope: MemberScope): ClubMemberDto
    fun toDto(entity: Club): ClubMemberDto
    fun toListDto(scope: MemberScope, dto: List<ClubMemberDto>): ClubMemberListDto
    fun toResponse(dto: ClubMemberListDto): ClubMemberListResponse
}