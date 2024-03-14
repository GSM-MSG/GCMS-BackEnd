package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.dto.SearchScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusDto
import java.time.LocalDate
import java.time.LocalTime

interface QueryCurrentAttendConditionService {
    fun execute(searchScheduleDto: SearchScheduleDto): List<UserAttendanceStatusDto>
}