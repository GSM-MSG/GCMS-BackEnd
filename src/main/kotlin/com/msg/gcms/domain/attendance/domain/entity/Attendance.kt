package com.msg.gcms.domain.attendance.domain.entity

import com.msg.gcms.domain.attendance.AttendanceStatus
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
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val schedule: Schedule
)