package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.Period
import java.time.LocalDate

data class ScheduleDto(
    val name: String,
    val date: LocalDate
) {
    data class ScheduleListDto(
        val schedule: ScheduleDto,
        val period: List<Period>
    )
}