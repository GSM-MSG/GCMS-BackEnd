package com.msg.gcms.domain.attendance.presentation.data.dto

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import java.util.*

data class UserAttendanceStatusListDto(
    val users: List<UserAttendanceStatusListDto.UserAttendanceStatusDto>
) {
    data class UserAttendanceStatusDto(
        val id: UUID,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val attendanceStatus: AttendanceStatus
    )
}