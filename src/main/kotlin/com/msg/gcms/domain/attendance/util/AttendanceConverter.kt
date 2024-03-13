package com.msg.gcms.domain.attendance.util

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.user.domain.entity.User

interface AttendanceConverter {
    fun toEntity(
        attendanceStatus: AttendanceStatus = AttendanceStatus.NOT_ATTENDED,
        user: User,
        schedule: Schedule
    ): Attendance
}