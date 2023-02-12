package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.presentation.data.dto.ClubApplyDto

interface ClubApplyService {
    fun execute(clubId: Long): ClubApplyDto
}