package com.msg.gcms.domain.club.domain.entity

import javax.persistence.*

@Entity
class OperationPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val field: String,

    val place: String,

    val subject: String,

    val content: String,

    val rule: String,


    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "monthly_plan_id")
    val monthlyPlan: List<MonthlyPlan>
)