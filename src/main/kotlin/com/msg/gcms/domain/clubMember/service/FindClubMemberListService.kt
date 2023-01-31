package com.msg.gcms.domain.clubMember.service

import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto

interface FindClubMemberListService {
    fun execute(clubId: Long): ClubMemberListDto
}