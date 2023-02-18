package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.presentation.data.dto.ApplicantListDto

interface ApplicantListService {
    fun execute(clubId: Long): ApplicantListDto
}