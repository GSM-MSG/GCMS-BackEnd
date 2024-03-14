package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import java.util.*

data class UserAttendanceStatusDto(
    val id: UUID,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val attendanceStatus: AttendanceStatus
) {
    data class UserAttendanceStatusListDto(
        val users: List<UserAttendanceStatusDto>
    )
}