package com.msg.gcms.domain.attendance.presentation.data.dto

import java.time.LocalDate
import java.time.LocalTime

data class SearchScheduleDto(
    val clubId: Long,
    val date: LocalDate?,
    val period: LocalTime?
)