package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.attendance.presentation.data.response.AttendSelfCheckResponseDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.service.FindAttendSelfCheckService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalTime

@ServiceWithReadOnlyTransaction
class FindAttendSelfCheckServiceImpl (
        private val userUtil: UserUtil,
        private val attendanceRepository: AttendanceRepository,
        private val clubRepository: ClubRepository,
        private val attendanceConverter: AttendanceConverter
): FindAttendSelfCheckService {
    override fun execute(id: Long): AttendSelfCheckResponseDto {
        val user = userUtil.fetchCurrentUser()
        val club = clubRepository.findByIdOrNull(id)
                ?: throw ClubNotFoundException()

        if (club.user != user) {
            throw NotClubMemberException()
        }

        val period = getCurrentPeriod()
        val attend = attendanceRepository.findByPeriodAndUser(period, user)

        return attendanceConverter.toDto(attend)
    }

    fun getCurrentPeriod(): Period {
        Period.values().reversed().forEach {
            if (LocalTime.now().isAfter(it.time))
                return it
        }   
        return Period.ELEVENTH
    }

}