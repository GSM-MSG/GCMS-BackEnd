package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto

interface AcceptApplicantService {
    fun execute(clubId: Long, acceptDto: AcceptDto)
}