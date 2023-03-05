package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto

interface FindPendingClubListService {
    fun execute(): List<PendingClubDto>
}