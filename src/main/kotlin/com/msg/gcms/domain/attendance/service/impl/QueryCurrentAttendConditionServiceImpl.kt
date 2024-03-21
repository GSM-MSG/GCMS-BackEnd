package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.ScheduleNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.dto.SearchScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusListDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.QueryCurrentAttendConditionService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithReadOnlyTransaction
class QueryCurrentAttendConditionServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter
) : QueryCurrentAttendConditionService {
    override fun execute(searchScheduleDto: SearchScheduleDto): UserAttendanceStatusListDto {
        val user: User = userUtil.fetchCurrentUser()

        val club = clubRepository.findByIdOrNull(searchScheduleDto.clubId) ?: throw ClubNotFoundException()

        when(user.roles) {
            listOf(Role.ROLE_STUDENT) -> {
                if (user != club.user)
                    throw HeadNotSameException()
            }
            listOf(Role.ROLE_ADMIN) -> {}
        }

        val schedule = scheduleRepository.queryByDate(club, searchScheduleDto.date)
            ?: throw ScheduleNotFoundException()

        val attendances = attendanceRepository.queryAllByPeriod(schedule, searchScheduleDto.period)

        return attendanceConverter.toListDto(attendances)
    }
}