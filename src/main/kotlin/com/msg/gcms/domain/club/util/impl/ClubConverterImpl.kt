package com.msg.gcms.domain.club.util.impl

import com.msg.gcms.domain.club.presentation.data.dto.ClubIdDto
import com.msg.gcms.domain.club.presentation.data.request.ClubIdRequestDto
import com.msg.gcms.domain.club.util.ClubConverter
import org.springframework.stereotype.Component

@Component
class ClubConverterImpl : ClubConverter {
    override fun toDto(clubIdRequestDto: ClubIdRequestDto): ClubIdDto =
        ClubIdDto(
            clubId = clubIdRequestDto.clubId
        )
}