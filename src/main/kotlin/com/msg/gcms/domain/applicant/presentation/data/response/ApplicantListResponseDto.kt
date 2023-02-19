package com.msg.gcms.domain.applicant.presentation.data.response

import com.msg.gcms.domain.club.enums.Scope
import java.util.*

data class ApplicantListResponseDto(
    val scope: Scope,
    val applicantList: List<ApplicantResponseDto>
) {
    data class ApplicantResponseDto(
        val uuid: UUID,
        val email: String,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?
    )
}
