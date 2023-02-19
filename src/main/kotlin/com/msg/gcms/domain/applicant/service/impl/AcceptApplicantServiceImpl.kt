package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.applicant.exception.DuplicateClubTypeApplicantException
import com.msg.gcms.domain.applicant.exception.NotApplicantException
import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.AcceptApplicantService
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class AcceptApplicantServiceImpl(
    private val clubRepository: ClubRepository,
    private val userUtil: UserUtil,
    private val applicantRepository: ApplicantRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userRepository: UserRepository
) : AcceptApplicantService {

    override fun execute(clubId: Long, acceptDto: AcceptDto) {
        val headUser: User = userUtil.fetchCurrentUser()

        val clubInfo: Club = clubRepository.findById(clubId)
            .orElseThrow { ClubNotFoundException() }

        val applicantUser: User = userRepository.findById(UUID.fromString(acceptDto.uuid))
            .orElseThrow { UserNotFoundException() }

        if (applicantRepository.countByClubTypeAndUser(clubInfo.type, applicantUser) != 0L)
            throw DuplicateClubTypeApplicantException()

        val clubMember = ClubMember(
            club = clubInfo,
            user = applicantUser
        )

        clubMemberRepository.save(clubMember)

        val applicant: Applicant =
            applicantRepository.findByUserIdAndClub(userId = UUID.fromString(acceptDto.uuid), club = clubInfo)
                ?: throw NotApplicantException()

        applicantRepository.delete(applicant)
    }
}