package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto.AttendanceBatchDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusBatchService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class UpdateAttendanceStatusBatchServiceImpl(
    private val clubRepository: ClubRepository,
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter,
    private val userUtil: UserUtil
) : UpdateAttendanceStatusBatchService {
    override fun execute(dto: AttendanceBatchDto, clubId: Long) {
        val currentUser = userUtil.fetchCurrentUser()

        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        if (club.user != currentUser && currentUser.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()

        val attendances = attendanceRepository.findAllByIdIn(dto.ids)
            .map {
                attendanceConverter.toEntity(
                    id = it.id,
                    attendanceStatus = dto.attendanceStatus,
                    user = it.user,
                    schedule = it.schedule,
                    period = it.period
                )
            }

        attendanceRepository.saveAll(attendances)

    }
}