package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.domain.entity.Attendance
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

        val schedules = scheduleConverter.toEntities(club, dto)
            .let { scheduleRepository.saveAll(it) }

        val attendances: List<Attendance> = schedules.flatMap { schedule ->
            members.map { member ->
                attendanceConverter.toEntity(
                    schedule = schedule,
                    user = member.user
                )
            }
        }

        attendanceRepository.saveAll(attendances)
    }
}