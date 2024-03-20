package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.AttendanceNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class UpdateAttendanceStatusServiceImpl(
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter,
    private val userUtil: UserUtil
) : UpdateAttendanceStatusService {
    override fun execute(dto: AttendanceDto) {
        val currentUser = userUtil.fetchCurrentUser()

        val attendance = attendanceRepository.findByIdOrNull(dto.id)
            ?: throw AttendanceNotFoundException()

        val club = attendance.schedule.club

        if (club.user != currentUser && currentUser.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()

        attendanceConverter.toEntity(
            id = attendance.id,
            attendanceStatus = dto.attendanceStatus,
            user = attendance.user,
            schedule = attendance.schedule,
            period = attendance.period
        ).let { attendanceRepository.save(it) }
    }
}