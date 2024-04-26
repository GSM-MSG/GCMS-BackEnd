package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.response.AttendSelfCheckResponseDto

interface FindAttendSelfCheckService {
    fun execute(id: Long) : AttendSelfCheckResponseDto
}