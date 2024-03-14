package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import java.util.UUID

data class AttendanceDto(
    val scheduleId: Long,
    val userId: UUID,
    val attendanceStatus: AttendanceStatus
){
    data class AttendanceListDto(
        val attendances: List<AttendanceDto>
    )
}