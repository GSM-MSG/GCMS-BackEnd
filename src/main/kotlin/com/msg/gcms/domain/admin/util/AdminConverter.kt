package com.msg.gcms.domain.admin.util

import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto
import com.msg.gcms.domain.admin.presentation.data.response.PendingClubResponse
import com.msg.gcms.domain.club.domain.entity.Club

interface AdminConverter {
    fun toDto(club: Club): PendingClubDto
    fun toResponse(dto: PendingClubDto): PendingClubResponse
}