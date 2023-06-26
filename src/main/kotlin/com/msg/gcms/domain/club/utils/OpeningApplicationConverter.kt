package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.OpeningApplication
import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto
import com.msg.gcms.domain.club.presentation.data.request.CreateOpeningApplicationRequest

interface OpeningApplicationConverter {
    fun toEntity(openingApplication: OpeningApplication, club: Club): Club
    fun toDto(createOpeningApplicationRequest: CreateOpeningApplicationRequest): ClubOpeningApplicationDto
}