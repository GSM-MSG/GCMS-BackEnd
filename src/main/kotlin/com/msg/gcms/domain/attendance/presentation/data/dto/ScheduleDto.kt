package com.msg.gcms.domain.attendance.presentation.data.dto

import java.time.LocalDate
import java.time.LocalTime

data class ScheduleDto (
    val name: String,
    val date: LocalDate,
    val period: LocalTime,
) {
    data class ScheduleListDto (
        val schedules: List<ScheduleDto>
    )
}