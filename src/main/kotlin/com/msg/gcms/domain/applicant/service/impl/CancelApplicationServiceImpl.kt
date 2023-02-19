package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.NotApplicantException
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.CancelApplicationService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CancelApplicationServiceImpl(
    private val clubRepository: ClubRepository,
    private val applicantRepository: ApplicantRepository,
    private val userUtil: UserUtil,
) : CancelApplicationService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val user = userUtil.fetchCurrentUser()
        val applicant = club.applicant
            .find { it.user == user }
            ?: throw NotApplicantException()
        applicantRepository.delete(applicant)
    }
}