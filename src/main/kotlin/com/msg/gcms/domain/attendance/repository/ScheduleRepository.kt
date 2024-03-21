package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface ScheduleRepository : CrudRepository<Schedule, Long>, CustomScheduleRepository {
    fun findAllByDate(localDate: LocalDate): List<Schedule>
}
