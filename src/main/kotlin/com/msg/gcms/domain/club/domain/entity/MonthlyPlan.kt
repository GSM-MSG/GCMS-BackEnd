package com.msg.gcms.domain.club.domain.entity

import javax.persistence.*

@Entity
class MonthlyPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val month: Long,

    val plan: String
)