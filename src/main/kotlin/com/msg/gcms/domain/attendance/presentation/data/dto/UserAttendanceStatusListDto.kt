package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import java.util.*

data class UserAttendanceStatusListDto(
    val users: List<UserAttendanceStatusDto>
) {
    data class UserAttendanceStatusDto(
        val uuid: UUID,
        val attendanceId: Long,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val attendanceStatus: AttendanceStatus
    )
}