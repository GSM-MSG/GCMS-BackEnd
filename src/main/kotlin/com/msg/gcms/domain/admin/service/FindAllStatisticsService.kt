package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.admin.presentation.data.dto.FindAllStatisticsDto
import com.msg.gcms.domain.club.enums.ClubType

interface FindAllStatisticsService {
    fun execute(clubType: ClubType): FindAllStatisticsDto
}