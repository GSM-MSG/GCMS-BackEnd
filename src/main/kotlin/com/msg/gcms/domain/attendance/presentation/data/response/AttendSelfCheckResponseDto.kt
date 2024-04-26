package com.msg.gcms.domain.attendance.presentation.data.response

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus

data class AttendSelfCheckResponseDto(
    val attendanceStatus: AttendanceStatus
)
