package com.msg.gcms.domain.club.domain.entity

import com.msg.gcms.domain.club.presentation.data.dto.OperationPlanDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class MonthlyPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val month: Long,

    val summaryPlan: String,

    val plan: String
)