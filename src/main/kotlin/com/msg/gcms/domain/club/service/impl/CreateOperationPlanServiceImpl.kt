package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.MonthlyPlan
import com.msg.gcms.domain.club.domain.entity.OperationPlan
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.domain.repository.MonthlyPlanRepository
import com.msg.gcms.domain.club.domain.repository.OperationPlanRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.presentation.data.dto.OperationPlanDto
import com.msg.gcms.domain.club.service.CreateOperationPlanService
import com.msg.gcms.domain.club.utils.OperationPlanConverter
import com.msg.gcms.global.annotation.ServiceWithTransaction
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class CreateOperationPlanServiceImpl(
    private val operationPlanRepository: OperationPlanRepository,
    private val monthlyPlanRepository: MonthlyPlanRepository,
    private val clubRepository: ClubRepository,
    private val operationPlanConverter: OperationPlanConverter
) : CreateOperationPlanService {
    override fun execute(clubId: Long, operationPlanDto: OperationPlanDto) {
        val foundClub: Club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()
        val monthlyPlan = operationPlanDto.monthlyPlan
                .map { MonthlyPlan(
                    month = it.month,
                    summaryPlan = it.summaryPlan,
                    plan = it.plan
                ) }

        monthlyPlanRepository.saveAll(monthlyPlan)

        val operationPlan = OperationPlan(
            field = operationPlanDto.field,
            place = operationPlanDto.place,
            subject = operationPlanDto.subject,
            content = operationPlanDto.content,
            rule = operationPlanDto.rule,
            monthlyPlan = monthlyPlan
        )

        operationPlanRepository.save(operationPlan)

        val club = operationPlanConverter.toEntity(operationPlan, foundClub)
        clubRepository.save(club)
    }
}