package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.OperationPlan
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

    override fun toEntity(operationPlan: OperationPlan, club: Club): Club =
        Club(
            id = club.id,
            name = club.name,
            bannerImg = club.bannerImg,
            content = club.content,
            notionLink = club.notionLink,
            teacher = club.teacher,
            contact = club.contact,
            type = club.type,
            isOpened = club.isOpened,
            user = club.user,
            activityImg = club.activityImg,
            applicant = club.applicant,
            clubMember = club.clubMember,
            clubStatus = club.clubStatus,
            operationPlan = operationPlan
        )
}