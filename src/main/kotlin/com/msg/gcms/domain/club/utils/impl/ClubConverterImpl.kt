package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.utils.ClubConverter
import org.springframework.stereotype.Component

@Component
class ClubConverterImpl: ClubConverter {
    override fun toDto(type: String): ClubTypeDto =
        ClubTypeDto(clubType = ClubType.valueOf(type))

    override fun toDto(club: Club): ClubListDto =
        ClubListDto(id = club.id, type = club.type, name = club.name, bannerUrl = club.bannerImg )

    override fun toResponseDto(dto: ClubListDto): ClubListResponseDto =
        ClubListResponseDto(id = dto.id, type = dto.type, name = dto.name, bannerUrl = dto.bannerUrl)
}