package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.Period
import java.time.LocalDate

data class SearchScheduleDto(
    val clubId: Long,
    val date: LocalDate?,
    val period: Period?
)