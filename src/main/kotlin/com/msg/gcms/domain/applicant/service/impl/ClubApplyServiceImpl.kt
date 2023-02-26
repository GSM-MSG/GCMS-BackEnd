package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.AlreadyClubMemberException
import com.msg.gcms.domain.applicant.exception.DuplicateClubTypeApplicantException
import com.msg.gcms.domain.applicant.presentation.data.dto.ClubApplyDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.ClubApplyService
import com.msg.gcms.domain.applicant.util.ApplicantSaveUtil
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ClubApplyServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val applicantSaveUtil: ApplicantSaveUtil,
    private val applicantRepository: ApplicantRepository
) : ClubApplyService {
    override fun execute(clubId: Long): ClubApplyDto {
        val club = clubRepository.findById(clubId)
            .orElseThrow { ClubNotFoundException() }
        val user = userUtil.fetchCurrentUser()
        if (club.clubMember.contains(clubMemberRepository.findByUserAndClub(user, club)) || club.user == user && club.type != ClubType.EDITORIAL)
            throw AlreadyClubMemberException()
        if (applicantRepository.countByClubTypeAndUser(club.type, user) != 0L && club.type != ClubType.EDITORIAL)
            throw DuplicateClubTypeApplicantException()
        val applicant = applicantSaveUtil.saveApplicant(club, user)
        return ClubApplyDto(applicant)
    }

}