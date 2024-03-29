package com.msg.gcms.domain.attendance.repository.impl

import com.msg.gcms.domain.attendance.domain.entity.QSchedule.schedule
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.repository.CustomScheduleRepository
import com.msg.gcms.domain.club.domain.entity.Club
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate

class CustomScheduleRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomScheduleRepository {
    override fun queryByDate(club: Club, date: LocalDate?): Schedule? {
        return jpaQueryFactory.selectFrom(schedule)
            .where(
                schedule.club.eq(club),
                dateEq(date)
            ).fetchOne()
    }

    private fun dateEq(date: LocalDate?): BooleanExpression =
        schedule.date.eq(date ?: LocalDate.now())
}