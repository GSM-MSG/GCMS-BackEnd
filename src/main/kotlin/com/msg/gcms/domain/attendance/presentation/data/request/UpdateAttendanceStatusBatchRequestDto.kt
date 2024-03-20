package com.msg.gcms.domain.attendance.presentation.data.request

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

data class UpdateAttendanceStatusBatchRequestDto(
    @field:NotNull
    @Enumerated(EnumType.STRING)
    val attendanceStatus: AttendanceStatus,

    @field:NotNull
    val attendanceIds: List<UUID>
)