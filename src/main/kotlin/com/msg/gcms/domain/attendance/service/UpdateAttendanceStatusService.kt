package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto

interface UpdateAttendanceStatusService {
    fun execute(dto: AttendanceDto)
}