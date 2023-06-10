package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.MonthlyPlan
import com.msg.gcms.domain.club.domain.entity.OperationPlan
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.domain.repository.MonthlyPlanRepository
import com.msg.gcms.domain.club.domain.repository.OperationPlanRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.presentation.data.dto.OperationPlanDto
import com.msg.gcms.domain.club.presentation.data.request.CreateOperationPlanRequest
import com.msg.gcms.domain.club.service.CreateOperationPlanService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateOperationPlanServiceImpl(
    private val operationPlanRepository: OperationPlanRepository,
    private val monthlyPlanRepository: MonthlyPlanRepository,
    private val clubRepository: ClubRepository
): CreateOperationPlanService {
    override fun execute(clubId: Long, operationPlanDto: OperationPlanDto) {
        val club: Club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()
        val monthlyPlan = operationPlanDto.monthlyPlan
                .map { MonthlyPlan(it) }

        monthlyPlanRepository.saveAll(monthlyPlan)

        val operationPlan = OperationPlan(
            field = operationPlanDto.field,
            place = operationPlanDto.place,
            subject = operationPlanDto.subject,
            content = operationPlanDto.content,
            rule = operationPlanDto.rule,
            monthlyPlan = monthlyPlan
        )

        club.updateOperationPlan(operationPlan)
        operationPlanRepository.save(operationPlan)
    }
}