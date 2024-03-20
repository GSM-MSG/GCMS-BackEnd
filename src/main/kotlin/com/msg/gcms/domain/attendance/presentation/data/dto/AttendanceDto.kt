package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus

data class AttendanceDto(
    val id: Long = 0L,
    val attendanceStatus: AttendanceStatus
) {
    data class AttendanceBatchDto(
        val ids: List<Long> = mutableListOf(),
        val attendanceStatus: AttendanceStatus
    )
}