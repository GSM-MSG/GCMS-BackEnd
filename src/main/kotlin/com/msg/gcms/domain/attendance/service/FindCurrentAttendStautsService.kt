package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.response.AttendSelfCheckResponseDto

interface FindCurrentAttendStautsService {
    fun execute(id: Long) : AttendSelfCheckResponseDto
}