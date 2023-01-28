package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto

interface ClubConverter {
    fun toDto(type: String): ClubTypeDto
    fun toDto(club: Club): ClubListDto
    fun toResponseDto(dto: ClubListDto): ClubListResponseDto
}