package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.OperationPlanDto

interface CreateOperationPlanService {
    fun execute(clubId: Long, operationPlanDto: OperationPlanDto)
}