package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.Period

interface CustomAttendanceRepository {
    fun queryAllByPeriod(schedule: Schedule, period: Period?): List<Attendance>
}