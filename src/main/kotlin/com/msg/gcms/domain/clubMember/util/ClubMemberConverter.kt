package com.msg.gcms.domain.clubMember.util

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberExitDto
import com.msg.gcms.domain.clubMember.presentation.data.request.ExitClubMemberRequest
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto
import com.msg.gcms.domain.clubMember.presentation.data.request.DelegateHeadRequest
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListDto
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse

interface ClubMemberConverter {
    fun toDto(entity: ClubMember, scope: MemberScope): ClubMemberListDto.SingleClubMemberDto
    fun toDto(entity: Club): ClubMemberListDto.SingleClubMemberDto
    fun toListDto(scope: MemberScope, dto: List<ClubMemberListDto.SingleClubMemberDto>): ClubMemberListDto
    fun toResponse(dto: ClubMemberListDto): ClubMemberListResponse
    fun toDto(clubId: Long, requestDto: ExitClubMemberRequest): ClubMemberExitDto
    fun toDto(delegateHeadRequest: DelegateHeadRequest): DelegateHeadDto
}