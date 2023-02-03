package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.enums.ClubType
import org.springframework.data.repository.CrudRepository
import com.msg.gcms.domain.user.domain.entity.User

interface ClubRepository : CrudRepository<Club, Long> {
    fun findByType(type: ClubType): List<Club>
    fun findByUser(user: User): List<Club>
}