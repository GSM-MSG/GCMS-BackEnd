package com.msg.gcms.domain.attendance.util.impl

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceStatusDto
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusRequestDto
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class AttendanceConverterImpl : AttendanceConverter {
    override fun toEntity(
        id: Long,
        attendanceStatus: AttendanceStatus,
        user: User,
        schedule: Schedule
    ): Attendance = Attendance(
        id = id,
        attendanceStatus = attendanceStatus,
        user = user,
        schedule = schedule
    )
    override fun toDto(user: User, attendance: Attendance): UserAttendanceStatusDto =
        UserAttendanceStatusDto(
            id = user.id,
            name = user.nickname,
            grade = user.grade,
            classNum = user.classNum,
            number = user.number,
            attendanceStatus = attendance.attendanceStatus
        )
    override fun toDto(attendanceStatusDto: UpdateAttendanceStatusRequestDto): AttendanceStatusDto = AttendanceStatusDto(
        scheduleId = attendanceStatusDto.scheduleId,
        attendanceStatus = attendanceStatusDto.attendanceStatus
    )

    override fun toListDto(attendances: List<Attendance>): UserAttendanceStatusDto.UserAttendanceStatusListDto =
        UserAttendanceStatusDto.UserAttendanceStatusListDto(
            users = attendances.map { toDto(it.user, it) }
        )
}