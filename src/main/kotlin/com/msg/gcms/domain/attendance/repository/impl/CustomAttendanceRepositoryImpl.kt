package com.msg.gcms.domain.attendance.repository.impl

import com.msg.gcms.domain.attendance.domain.entity.Attendance
import com.msg.gcms.domain.attendance.domain.entity.QAttendance.attendance
import com.msg.gcms.domain.attendance.domain.entity.Schedule
import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.attendance.repository.CustomAttendanceRepository
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory

class CustomAttendanceRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomAttendanceRepository {
    override fun queryAllByPeriod(schedule: Schedule, period: Period?): List<Attendance> {
        return jpaQueryFactory.selectFrom(attendance)
            .where(
                attendance.schedule.eq(schedule),
                periodEq(period)
            ).fetch()
    }

    private fun periodEq(period: Period?): BooleanExpression? =
        if(period == null) null else attendance.period.eq(period)

}