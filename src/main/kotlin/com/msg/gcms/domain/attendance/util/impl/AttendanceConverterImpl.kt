package com.msg.gcms.domain.attendance.util.impl

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class AttendanceConverterImpl : AttendanceConverter {
    override fun toEntity(
        attendanceStatus: AttendanceStatus,
        user: User,
        schedule: Schedule
    ): Attendance = Attendance(
        attendanceStatus = attendanceStatus,
        user = user,
        schedule = schedule
    )
}