package com.msg.gcms.domain.applicant.presentation.data.dto

import com.msg.gcms.domain.club.enums.Scope
import java.util.UUID

data class ApplicantListDto(
    val scope: Scope,
    val applicantList: List<ApplicantDto>
) {
    data class ApplicantDto(
        val uuid: UUID,
        val email: String,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?
    )
}