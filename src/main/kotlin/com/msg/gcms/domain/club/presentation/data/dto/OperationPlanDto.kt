package com.msg.gcms.domain.club.presentation.data.dto

import com.msg.gcms.domain.club.presentation.data.request.CreateOperationPlanRequest

data class OperationPlanDto(
    val field: String,

    val place: String,

    val subject: String,

    val content: String,

    val rule: String,

    val monthlyPlan: List<MonthlyPlanDto>
) {
    data class MonthlyPlanDto(
        val month: Long,

        val plan: String
    ) {
        constructor(createMonthlyPlanRequest: CreateOperationPlanRequest.CreateMonthlyPlanRequest): this(
            month = createMonthlyPlanRequest.month,
            plan = createMonthlyPlanRequest.plan
        )
    }
}