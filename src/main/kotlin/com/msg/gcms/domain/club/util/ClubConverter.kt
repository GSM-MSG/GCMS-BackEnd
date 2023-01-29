package com.msg.gcms.domain.club.util

import com.msg.gcms.domain.club.presentation.data.dto.ClubIdDto
import com.msg.gcms.domain.club.presentation.data.request.ClubIdRequestDto

interface ClubConverter {
    fun toDto(clubIdRequestDto: ClubIdRequestDto): ClubIdDto
}