package com.msg.gcms.domain.applicant.domain.repository

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.repository.CrudRepository

interface ApplicantRepository : CrudRepository<Applicant, Long> {
    fun existsByUserAndClub(user: User, club: Club): Boolean
}