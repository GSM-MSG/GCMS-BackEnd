package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.ScheduleNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto.AttendanceBatchDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusBatchService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class UpdateAttendanceStatusBatchServiceImpl(
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter,
    private val userUtil: UserUtil
) : UpdateAttendanceStatusBatchService {
    override fun execute(dto: AttendanceBatchDto) {
        val currentUser = userUtil.fetchCurrentUser()

        val schedule = scheduleRepository.findByIdOrNull(dto.scheduleId)
            ?: throw ScheduleNotFoundException()

        if (schedule.club.user != currentUser && currentUser.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()

        val users = userRepository.findAllByIdIn(dto.userIds)

        val attendances = attendanceRepository.findAllByUserInAndSchedule(users, schedule)
            .map {
                attendanceConverter.toEntity(
                    id = it.id,
                    attendanceStatus = dto.attendanceStatus,
                    user = it.user,
                    schedule = it.schedule
                )
            }

        attendanceRepository.saveAll(attendances)

    }
}