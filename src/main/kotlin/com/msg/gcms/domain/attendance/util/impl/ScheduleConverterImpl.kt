package com.msg.gcms.domain.attendance.util.impl

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto.ScheduleListDto
import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import com.msg.gcms.domain.club.domain.entity.Club
import org.springframework.stereotype.Component

@Component
class ScheduleConverterImpl : ScheduleConverter {
    override fun toEntity(club: Club, scheduleDto: ScheduleDto): Schedule = Schedule(
        id = 0L,
        name = scheduleDto.name,
        date = scheduleDto.date,
        period = scheduleDto.period,
        club = club,
    )

    override fun toEntities(club: Club, scheduleDto: ScheduleListDto): List<Schedule> = scheduleDto.schedules.map {
        toEntity(
            club = club,
            scheduleDto = it
        ) }

    override fun toDto(scheduleRequestDto: CreateScheduleRequestDto): ScheduleListDto = ScheduleListDto(
        scheduleRequestDto.periods.map {
            ScheduleDto(
                name = scheduleRequestDto.name,
                date = scheduleRequestDto.date,
                period = it
            )
        }
    )
}