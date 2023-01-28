package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.utils.ClubConverter
import org.springframework.stereotype.Component

@Component
class ClubConverterImpl: ClubConverter {
    override fun toDto(type: String): ClubTypeDto =
        ClubTypeDto(clubType = ClubType.valueOf(type))

    override fun toDto(club: Club): ClubListDto =
        ClubListDto(id = club.id, type = club.type, title = club.name, bannerUrl = club.bannerImg )
}