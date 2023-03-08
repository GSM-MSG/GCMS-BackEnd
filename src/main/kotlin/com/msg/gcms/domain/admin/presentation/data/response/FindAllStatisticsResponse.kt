package com.msg.gcms.domain.admin.presentation.data.response

data class FindAllStatisticsResponse (
    val total: Int,
    val applicantCount: Int,
    val nonApplicantCount: Int
)
