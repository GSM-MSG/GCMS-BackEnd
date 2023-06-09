package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.MonthlyPlan
import org.springframework.data.jpa.repository.JpaRepository

interface MonthlyPlanRepository: JpaRepository<MonthlyPlan, Long> {
}