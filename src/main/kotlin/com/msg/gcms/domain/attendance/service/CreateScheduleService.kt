package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.dto.ScheduleDto.ScheduleListDto

interface CreateScheduleService {
    fun execute(clubId: Long, dto: ScheduleListDto)
}