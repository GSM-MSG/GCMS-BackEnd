package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.NotApplicantException
import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.AcceptApplicantService
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class AcceptApplicantServiceImpl(
    private val clubRepository: ClubRepository,
    private val applicantRepository: ApplicantRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userRepository: UserRepository,
    private val applicantConverter: ApplicantConverter,
    private val userUtil: UserUtil
) : AcceptApplicantService {

    override fun execute(clubId: Long, acceptDto: AcceptDto) {
        val clubInfo = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        val headUserInfo: User = userUtil.fetchCurrentUser()

        if (clubInfo.user != headUserInfo)
            throw HeadNotSameException()

        val applicantUser: User = userRepository.findByIdOrNull(UUID.fromString(acceptDto.uuid))
            ?: throw UserNotFoundException()

        val applicant = clubInfo.applicant
            .find { it.user == applicantUser }
            ?: throw NotApplicantException()

        applicantRepository.delete(applicant)

        val clubMember = applicantConverter.toEntity(clubInfo, applicantUser)
        clubMemberRepository.save(clubMember)

    }
}