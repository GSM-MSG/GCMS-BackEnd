package com.msg.gcms.domain.attendance.repository

import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.club.domain.entity.Club
import java.time.LocalDate

interface CustomScheduleRepository {
    fun queryByDate(club: Club, date: LocalDate?): Schedule?
    fun existByDateAndPeriodsAndClub(date: LocalDate, period: List<Period>, club: Club): Boolean
}