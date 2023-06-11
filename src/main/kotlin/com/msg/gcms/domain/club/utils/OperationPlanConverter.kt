package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.OperationPlan
import com.msg.gcms.domain.club.presentation.data.dto.OperationPlanDto
import com.msg.gcms.domain.club.presentation.data.request.CreateOperationPlanRequest

interface OperationPlanConverter {
    fun toDto(createOperationPlanRequest: CreateOperationPlanRequest): OperationPlanDto
    fun toDto(monthlyPlan: List<CreateOperationPlanRequest.CreateMonthlyPlanRequest>): List<OperationPlanDto.MonthlyPlanDto>
    fun toEntity(clubId: Long, operationPlan: OperationPlan, club: Club): Club =
        Club(
            id = clubId,
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