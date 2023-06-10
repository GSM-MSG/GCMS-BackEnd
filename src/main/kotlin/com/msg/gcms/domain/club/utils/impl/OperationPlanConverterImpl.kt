package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.presentation.data.dto.OperationPlanDto
import com.msg.gcms.domain.club.presentation.data.request.CreateOperationPlanRequest
import com.msg.gcms.domain.club.utils.OperationPlanConverter
import org.springframework.stereotype.Component

@Component
class OperationPlanConverterImpl: OperationPlanConverter {
    override fun toDto(createOperationPlanRequest: CreateOperationPlanRequest): OperationPlanDto =
        OperationPlanDto(
            field = createOperationPlanRequest.field,
            place = createOperationPlanRequest.place,
            subject = createOperationPlanRequest.subject,
            content = createOperationPlanRequest.content,
            rule = createOperationPlanRequest.rule,
            monthlyPlan = toDto(createOperationPlanRequest.monthlyPlan)
        )

    override fun toDto(monthlyPlan: List<CreateOperationPlanRequest.CreateMonthlyPlanRequest>): List<OperationPlanDto.MonthlyPlanDto> =
        monthlyPlan.map { OperationPlanDto.MonthlyPlanDto(it) }
}