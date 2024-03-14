package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceDto.AttendanceBatchDto

interface UpdateAttendanceStatusBatchService {
    fun execute(dto: AttendanceBatchDto)
}