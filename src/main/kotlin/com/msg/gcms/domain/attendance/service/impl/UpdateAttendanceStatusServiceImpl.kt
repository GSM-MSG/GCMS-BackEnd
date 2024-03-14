package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.AttendanceNotFoundException
import com.msg.gcms.domain.attendance.exception.ScheduleNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceStatusDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.annotation.ServiceWithTransaction
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@ServiceWithTransaction
class UpdateAttendanceStatusServiceImpl(
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter
) : UpdateAttendanceStatusService {
    override fun execute(userId: UUID, dto: AttendanceStatusDto) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        val schedule = scheduleRepository.findByIdOrNull(dto.scheduleId)
            ?: throw ScheduleNotFoundException()

        if (schedule.club.user != user && user.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()

        val attendance = attendanceRepository.findByUserAndSchedule(user, schedule)
            ?: throw AttendanceNotFoundException()

        attendanceConverter.toEntity(
            id = attendance.id,
            attendanceStatus = dto.attendanceStatus,
            user = attendance.user,
            schedule = attendance.schedule
        ).let { attendanceRepository.save(it) }
    }
}