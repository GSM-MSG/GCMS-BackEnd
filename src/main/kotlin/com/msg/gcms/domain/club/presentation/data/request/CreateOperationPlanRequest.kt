package com.msg.gcms.domain.club.presentation.data.request

data class CreateOperationPlanRequest(
    val field: String,

    val place: String,

    val subject: String,

    val content: String,

    val rule: String,

    val monthlyPlan: List<CreateMonthlyPlanRequest>
) {
    data class CreateMonthlyPlanRequest(
        val month: Long,

        val plan: String
    )
}