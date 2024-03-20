package com.msg.gcms.domain.attendance.util.impl

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto.ScheduleListDto
import com.msg.gcms.domain.attendance.presentation.data.dto.SearchScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import com.msg.gcms.domain.club.domain.entity.Club
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

@Component
class ScheduleConverterImpl : ScheduleConverter {
    override fun toEntity(club: Club, scheduleDto: ScheduleDto): Schedule = Schedule(
        id = 0L,
        name = scheduleDto.name,
        date = scheduleDto.date,
        club = club,
    )

    override fun toDto(scheduleRequestDto: CreateScheduleRequestDto): ScheduleListDto = ScheduleListDto(
        schedule = ScheduleDto(
            name = scheduleRequestDto.name,
            date = scheduleRequestDto.date
        ),
        period = scheduleRequestDto.periods
    )

    override fun toDto(clubId: Long, date: LocalDate?, period: LocalTime?): SearchScheduleDto = SearchScheduleDto(
        clubId = clubId,
        date = date,
        period = period
    )
}