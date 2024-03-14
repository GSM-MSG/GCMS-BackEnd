package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.dto.SearchScheduleDto
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusListDto

interface QueryCurrentAttendConditionService {
    fun execute(searchScheduleDto: SearchScheduleDto): List<UserAttendanceStatusListDto>
}