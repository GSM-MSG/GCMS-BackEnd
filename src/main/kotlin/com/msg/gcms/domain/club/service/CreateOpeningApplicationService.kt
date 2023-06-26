package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto

interface CreateOpeningApplicationService {
    fun execute(clubId: Long, clubOpeningApplicationDto: ClubOpeningApplicationDto)
}