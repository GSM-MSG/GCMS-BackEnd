package com.msg.gcms.domain.attendance.presentation.data.request

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

data class UpdateAttendanceStatusRequestDto(
    @field:NotNull
    val attendanceId: Long,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val attendanceStatus: AttendanceStatus
)