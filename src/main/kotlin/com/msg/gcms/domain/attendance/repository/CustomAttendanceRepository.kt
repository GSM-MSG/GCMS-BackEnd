package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import java.time.LocalTime

interface CustomAttendanceRepository {
    fun queryAllByPeriod(schedule: Schedule, period: LocalTime?): List<Attendance>
}