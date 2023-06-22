package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto

interface CreateClubOpeningApplicationService {
    fun execute(clubId: Long, clubOpeningApplicationDto: ClubOpeningApplicationDto)
}