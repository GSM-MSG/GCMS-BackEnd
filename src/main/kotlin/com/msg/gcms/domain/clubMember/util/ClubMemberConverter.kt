package com.msg.gcms.domain.clubMember.util

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse

interface ClubMemberConverter {
    fun toDto(entity: ClubMember, scope: MemberScope): ClubMemberListDto.SingleClubMemberDto
    fun toDto(entity: Club): ClubMemberListDto.SingleClubMemberDto
    fun toListDto(scope: MemberScope, dto: List<ClubMemberListDto.SingleClubMemberDto>): ClubMemberListDto
    fun toResponse(dto: ClubMemberListDto): ClubMemberListResponse
}