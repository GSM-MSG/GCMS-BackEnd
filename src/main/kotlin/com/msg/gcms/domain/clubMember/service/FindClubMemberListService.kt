package com.msg.gcms.domain.clubMember.service

import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListDto

interface FindClubMemberListService {
    fun execute(clubId: Long): ClubMemberListDto
}