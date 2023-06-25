package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.ClubOpeningApplication
import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto
import com.msg.gcms.domain.club.presentation.data.request.CreateClubOpeningApplicationRequest

interface ClubOpeningApplicationConverter {
    fun toEntity(clubOpeningApplication: ClubOpeningApplication, club: Club): Club
    fun toDto(createClubOpeningApplicationRequest: CreateClubOpeningApplicationRequest): ClubOpeningApplicationDto
}