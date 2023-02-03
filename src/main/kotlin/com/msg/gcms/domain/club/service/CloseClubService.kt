package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto

interface CloseClubService {
    fun execute(clubId: Long): ClubStatusDto
}