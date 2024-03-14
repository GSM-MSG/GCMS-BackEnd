package com.msg.gcms.domain.attendance.util.impl

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto.AttendanceBatchDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusBatchRequestDto
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusListDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusRequestDto
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component
import java.util.UUID

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

    override fun toDto(user: User, attendance: Attendance): UserAttendanceStatusListDto.UserAttendanceStatusDto =
        UserAttendanceStatusListDto.UserAttendanceStatusDto(
            id = user.id,
            name = user.nickname,
            grade = user.grade,
            classNum = user.classNum,
            number = user.number,
            attendanceStatus = attendance.attendanceStatus
        )
    override fun toDto(attendanceStatusDto: UpdateAttendanceStatusRequestDto, userId: UUID): AttendanceDto = AttendanceDto(
        scheduleId = attendanceStatusDto.scheduleId,
        attendanceStatus = attendanceStatusDto.attendanceStatus,
        userId = userId
    )

    override fun toDto(attendanceStatusDto: UpdateAttendanceStatusBatchRequestDto): AttendanceBatchDto = AttendanceBatchDto(
        scheduleId = attendanceStatusDto.scheduleId,
        attendanceStatus = attendanceStatusDto.attendanceStatus,
        userIds = attendanceStatusDto.userIds
    )

    override fun toListDto(attendances: List<Attendance>): UserAttendanceStatusListDto =
        UserAttendanceStatusListDto(
            users = attendances.map { toDto(it.user, it) }
        )
}