package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.repository.CrudRepository

interface ClubRepository : CrudRepository<Club, Long> {
    fun findByTypeAndClubStatus(type: ClubType, status: ClubStatus): List<Club>
    fun findAllByClubStatusAndUser(status: ClubStatus, user: User): List<Club>
    fun findByType(type: ClubType): List<Club>
    fun findAllByClubStatus(status: ClubStatus): List<Club>
    fun findByUser(user: User): List<Club>
    fun existsByUserAndType(user: User, type: ClubType): Boolean
    fun existsByUser(user: User): Boolean
    fun existsByNameAndType(name: String, type: ClubType): Boolean
    fun existsByTypeAndUserAndClubStatus(type: ClubType, user: User, clubStatus: ClubStatus): Boolean
}