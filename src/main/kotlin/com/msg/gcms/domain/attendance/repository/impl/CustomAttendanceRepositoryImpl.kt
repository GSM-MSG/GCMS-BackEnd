package com.msg.gcms.domain.attendance.repository.impl

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.QAttendance.attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.repository.CustomAttendanceRepository
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalTime

class CustomAttendanceRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomAttendanceRepository {
    override fun queryAllByPeriod(schedule: Schedule, period: LocalTime?): List<Attendance> {
        return jpaQueryFactory.selectFrom(attendance)
            .where(
                attendance.schedule.eq(schedule),
                periodEq(period)
            ).fetch()
    }

    private fun periodEq(period: LocalTime?): BooleanExpression =
        if(period != null) attendance.period.eq(period) else attendance.period.between(LocalTime.now().minusHours(1), LocalTime.now())

}