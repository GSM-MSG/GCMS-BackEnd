package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.OpeningApplicationDto

interface CreateOpeningApplicationService {

    fun execute(clubId: Long, openingApplicationDto: OpeningApplicationDto)
}