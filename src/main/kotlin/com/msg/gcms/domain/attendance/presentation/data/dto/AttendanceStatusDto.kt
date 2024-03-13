package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus

data class AttendanceStatusDto(
    val scheduleId: Long,
    val attendanceStatus: AttendanceStatus
)