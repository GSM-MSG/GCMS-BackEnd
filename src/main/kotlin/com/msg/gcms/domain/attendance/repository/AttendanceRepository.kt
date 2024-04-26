package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository

interface AttendanceRepository : CrudRepository<Attendance, Long>, CustomAttendanceRepository {
    @EntityGraph(attributePaths = ["user"])
    fun findAllByScheduleAndPeriod(schedule: Schedule, period: Period): List<Attendance>
    fun findAllByIdIn(ids: List<Long>): List<Attendance>
    @EntityGraph(attributePaths = ["user"])
    fun findAllBySchedule(schedule: Schedule): List<Attendance>

    fun findByScheduleAndPeriodAndUser (schedule: Schedule, period: Period, user: User): Attendance?
}