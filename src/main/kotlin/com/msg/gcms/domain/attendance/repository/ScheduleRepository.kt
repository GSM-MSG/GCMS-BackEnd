package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import org.springframework.data.repository.CrudRepository

interface ScheduleRepository : CrudRepository<Schedule, Long>, CustomScheduleRepository