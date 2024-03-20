package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.AttendanceNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class UpdateAttendanceStatusServiceImpl(
    private val clubRepository: ClubRepository,
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter,
    private val userUtil: UserUtil
) : UpdateAttendanceStatusService {
    override fun execute(dto: AttendanceDto, clubId: Long) {
        val currentUser = userUtil.fetchCurrentUser()

        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        if (club.user != currentUser && currentUser.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()

        val attendance = attendanceRepository.findByIdOrNull(dto.id)
            ?: throw AttendanceNotFoundException()

        attendanceConverter.toEntity(
            id = attendance.id,
            attendanceStatus = dto.attendanceStatus,
            user = attendance.user,
            schedule = attendance.schedule,
            period = attendance.period
        ).let { attendanceRepository.save(it) }
    }
}