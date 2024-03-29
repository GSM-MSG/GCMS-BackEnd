package com.msg.gcms.domain.attendance.domain.entity

import com.msg.gcms.domain.club.domain.entity.Club
import java.time.LocalDate
import javax.persistence.*

@Entity
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(columnDefinition = "VARCHAR(30)", name = "name")
    val name: String,

    @Column(columnDefinition = "DATE", name = "date", updatable = false)
    val date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "club_id")
    val club: Club
)