package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.AlreadyScheduleExistException
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto.ScheduleListDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.CreateScheduleService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.annotation.ServiceWithTransaction
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class CreateScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val clubRepository: ClubRepository,
    private val attendanceRepository: AttendanceRepository,
    private val scheduleConverter: ScheduleConverter,
    private val attendanceConverter: AttendanceConverter
) : CreateScheduleService {
    override fun execute(clubId: Long, dto: ScheduleListDto) {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        val members = clubMemberRepository.findByClub(club)

        val schedule = scheduleRepository.findByClubAndDate(club, dto.schedule.date)
            ?: scheduleConverter.toEntity(club, dto.schedule)

        scheduleRepository.save(schedule)

        if(scheduleRepository.existByDateAndPeriods(dto.schedule.date, dto.period))
            throw AlreadyScheduleExistException()

        val attendances = dto.period.flatMap { period ->

            members.map { member ->
                attendanceConverter.toEntity(
                    schedule = schedule,
                    user = member.user,
                    period = period
                )
            }
        }

        attendanceRepository.saveAll(attendances)
    }
}