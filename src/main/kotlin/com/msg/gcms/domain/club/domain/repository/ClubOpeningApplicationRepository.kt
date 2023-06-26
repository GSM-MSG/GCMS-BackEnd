package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.OpeningApplication
import org.springframework.data.jpa.repository.JpaRepository

interface ClubOpeningApplicationRepository : JpaRepository<OpeningApplication, Long>