package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import org.springframework.data.repository.CrudRepository

interface AttendanceRepository : CrudRepository<Attendance, Long>