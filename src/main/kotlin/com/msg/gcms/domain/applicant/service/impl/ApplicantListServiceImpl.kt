package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.presentation.data.dto.ApplicantListDto
import com.msg.gcms.domain.applicant.service.ApplicantListService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplicantListServiceImpl: ApplicantListService {
    override fun execute(clubId: Long): ApplicantListDto {
        TODO("Not yet implemented")
    }
}