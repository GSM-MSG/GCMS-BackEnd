package com.msg.gcms.domain.attendance.util

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto.AttendanceBatchDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusBatchRequestDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusRequestDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusListDto
import java.util.*

interface AttendanceConverter {
    fun toEntity(
        id: Long = 0L,
        attendanceStatus: AttendanceStatus = AttendanceStatus.NOT_ATTENDED,
        user: User,
        schedule: Schedule
    ): Attendance
    fun toDto(attendanceStatusDto: UpdateAttendanceStatusRequestDto, userId: UUID): AttendanceDto
    fun toDto(attendanceStatusDto: UpdateAttendanceStatusBatchRequestDto): AttendanceBatchDto
    fun toDto(user:User, attendance: Attendance): UserAttendanceStatusListDto.UserAttendanceStatusDto
    fun toListDto(attendances: List<Attendance>): UserAttendanceStatusListDto
}