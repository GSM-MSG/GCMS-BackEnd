package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.attendance.exception.AttendanceNotFoundException
import com.msg.gcms.domain.attendance.exception.ScheduleNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.response.AttendSelfCheckResponseDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.QueryCurrentAttendStatusService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.time.LocalTime

@ServiceWithReadOnlyTransaction
class QueryCurrentAttendStatusServiceImpl (
    private val userUtil: UserUtil,
    private val attendanceRepository: AttendanceRepository,
    private val clubRepository: ClubRepository,
    private val attendanceConverter: AttendanceConverter,
    private val clubMemberRepository: ClubMemberRepository,
    private val scheduleRepository: ScheduleRepository
): QueryCurrentAttendStatusService {
    override fun execute(id: Long): AttendSelfCheckResponseDto {
        val user = userUtil.fetchCurrentUser()
        val club = clubRepository.findByIdOrNull(id)
                ?: throw ClubNotFoundException()

        if (!clubMemberRepository.existsByUserAndClub(user, club)) {
            throw NotClubMemberException()
        }

        val period = getCurrentPeriod()

        val schedule = scheduleRepository.findByClubAndDate(club, LocalDate.now()) ?: throw ScheduleNotFoundException()

        val attend = attendanceRepository.findByScheduleAndPeriodAndUser(schedule, period, user) ?: throw AttendanceNotFoundException()

        return attendanceConverter.toDto(attend);
    }

    fun getCurrentPeriod(): Period {
        Period.values().reversed().forEach {
            if (LocalTime.now().isAfter(it.time))
                return it
        }   
        return Period.ELEVENTH
    }

}