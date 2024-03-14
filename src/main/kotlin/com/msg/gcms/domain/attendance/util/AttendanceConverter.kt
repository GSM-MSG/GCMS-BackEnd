package com.msg.gcms.domain.attendance.util

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceStatusDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusRequestDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusDto

interface AttendanceConverter {
    fun toEntity(
        id: Long = 0L,
        attendanceStatus: AttendanceStatus = AttendanceStatus.NOT_ATTENDED,
        user: User,
        schedule: Schedule
    ): Attendance
    fun toDto(user:User, attendance: Attendance): UserAttendanceStatusDto
    fun toDto(attendanceStatusDto: UpdateAttendanceStatusRequestDto): AttendanceStatusDto
}