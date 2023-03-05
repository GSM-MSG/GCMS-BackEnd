package com.msg.gcms.domain.admin.util.impl

import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto
import com.msg.gcms.domain.admin.presentation.data.response.PendingClubResponse
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.club.domain.entity.Club
import org.springframework.stereotype.Component

@Component
class AdminConverterImpl : AdminConverter {
    override fun toDto(club: Club): PendingClubDto =
        PendingClubDto(
            bannerImg = club.bannerImg,
            name = club.name,
            type = club.type,
            content = club.content
        )

    override fun toResponse(dto: PendingClubDto): PendingClubResponse =
        PendingClubResponse(
            bannerImg = dto.bannerImg,
            name = dto.name,
            type = dto.type,
            content = dto.content
        )
}