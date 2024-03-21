package com.msg.gcms.domain.attendance.domain.entity

import com.msg.gcms.domain.attendance.domain.enums.AttendanceStatus
import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.user.domain.entity.User
import javax.persistence.*

@Entity
class Attendance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)", name = "attendance_status")
    val attendanceStatus: AttendanceStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)", name = "period")
    val period: Period,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    val schedule: Schedule
)