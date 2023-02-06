package com.msg.gcms.domain.clubMember.service

import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberExitDto

interface ExitClubMemberService {
    fun execute(clubMemberExitDto: ClubMemberExitDto)
}