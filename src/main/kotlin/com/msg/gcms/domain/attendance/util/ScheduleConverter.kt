package com.msg.gcms.domain.attendance.util

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto.ScheduleListDto
import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.club.domain.entity.Club

interface ScheduleConverter {
    fun toEntity(club: Club, scheduleDto: ScheduleDto): Schedule
    fun toEntities(club: Club, scheduleDto: ScheduleListDto): List<Schedule>
    fun toDto(scheduleRequestDto: CreateScheduleRequestDto): ScheduleListDto
}