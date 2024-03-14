package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.repository.CrudRepository

interface AttendanceRepository : CrudRepository<Attendance, Long> {
    fun findByUserAndSchedule(user: User, schedule: Schedule): Attendance?
}