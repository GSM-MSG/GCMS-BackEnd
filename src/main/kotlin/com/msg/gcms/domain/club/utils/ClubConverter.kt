package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.user.domain.entity.User

interface ClubConverter {
    fun toDto(createClubRequest: CreateClubRequest): ClubDto
    fun toEntity(clubDto: ClubDto, user: User): Club
}