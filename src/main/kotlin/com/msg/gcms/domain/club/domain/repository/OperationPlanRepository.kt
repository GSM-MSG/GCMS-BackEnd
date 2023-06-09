package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.OperationPlan
import org.springframework.data.jpa.repository.JpaRepository

interface OperationPlanRepository: JpaRepository<OperationPlan, Long> {
}