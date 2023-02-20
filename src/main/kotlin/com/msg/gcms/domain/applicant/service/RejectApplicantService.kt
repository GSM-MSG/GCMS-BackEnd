package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.presentation.data.dto.RejectDto

interface RejectApplicantService {
    fun execute(clubId: Long, rejectDto: RejectDto)
}