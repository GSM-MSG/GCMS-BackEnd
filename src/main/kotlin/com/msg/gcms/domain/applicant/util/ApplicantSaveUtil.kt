package com.msg.gcms.domain.applicant.util

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class ApplicantSaveUtil(
    private val applicantRepository: ApplicantRepository
) {
    fun saveApplicant(club: Club, user: User): Applicant{
        val applicant = Applicant(club = club, user = user)
        return applicantRepository.save(applicant)
    }
}