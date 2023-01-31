package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.Club
import org.springframework.data.repository.CrudRepository
import com.msg.gcms.domain.club.enums.ClubType

interface ClubRepository: CrudRepository<Club, Long> {
    fun findByType(type: ClubType): List<Club>
}