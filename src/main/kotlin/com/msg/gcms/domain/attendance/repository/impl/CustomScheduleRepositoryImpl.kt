package com.msg.gcms.domain.attendance.repository.impl

import com.msg.gcms.domain.attendance.domain.entity.QSchedule.schedule
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.repository.CustomScheduleRepository
import com.msg.gcms.domain.club.domain.entity.Club
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate
import java.time.LocalTime

class CustomScheduleRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomScheduleRepository {
    override fun queryByDateAndPeriod(club: Club, date: LocalDate?, period: LocalTime?): Schedule? {
        return jpaQueryFactory.selectFrom(schedule)
            .where(
                schedule.club.eq(club),
                dateEq(date),
                periodEq(period)
            ).fetchOne()
    }

    private fun dateEq(date: LocalDate?): BooleanExpression? =
        if(date != null) schedule.date.eq(date) else null

    private fun periodEq(period: LocalTime?): BooleanExpression? =
        if(period != null) schedule.period.eq(period) else null

}