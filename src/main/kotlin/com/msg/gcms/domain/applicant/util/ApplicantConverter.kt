package com.msg.gcms.domain.applicant.util

import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import org.springframework.stereotype.Component

@Component
class ApplicantConverter {
    fun toDto(acceptRequestDto: AcceptRequestDto): AcceptDto =
        AcceptDto(
            uuid = acceptRequestDto.uuid
        )
}