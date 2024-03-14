package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.club.domain.entity.Club
import java.time.LocalDate
import java.time.LocalTime

interface CustomScheduleRepository {
    fun queryByDateAndPeriod(club: Club, date: LocalDate?, period: LocalTime?): Schedule?
}