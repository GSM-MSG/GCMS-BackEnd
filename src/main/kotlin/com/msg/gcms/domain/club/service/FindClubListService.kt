package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto

interface FindClubListService {
    fun execute(clubTypeDto: ClubTypeDto): List<ClubListDto>
}