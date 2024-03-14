package com.msg.gcms.domain.attendance.service

import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceStatusDto
import java.util.UUID

interface UpdateAttendanceStatusService {
    fun execute(userId: UUID, dto: AttendanceStatusDto)
}