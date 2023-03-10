package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.admin.exception.NotAceessAdminException
import com.msg.gcms.domain.applicant.exception.AlreadyClubMemberException
import com.msg.gcms.domain.applicant.exception.DuplicateClubTypeApplicantException
import com.msg.gcms.domain.applicant.exception.SameClubApplicantException
import com.msg.gcms.domain.applicant.presentation.data.dto.ClubApplyDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.ClubApplyService
import com.msg.gcms.domain.applicant.util.ApplicantSaveUtil
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
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
    private val applicantRepository: ApplicantRepository,
    private val messageSendUtil: MessageSendUtil
) : ClubApplyService {
    override fun execute(clubId: Long): ClubApplyDto {
        val club = clubRepository.findById(clubId)
            .orElseThrow { ClubNotFoundException() }
        val user = userUtil.fetchCurrentUser()

        if(applicantRepository.existsByUserAndClub(user, club))
            throw SameClubApplicantException()

        val clubMember = clubMemberRepository.findByUserAndClub(user, club)
        if (club.clubMember.contains(clubMember) || club.user == user && club.type != ClubType.EDITORIAL)
            throw AlreadyClubMemberException()

        val countByClubTypeAndUser = applicantRepository.countByClubTypeAndUser(club.type, user)
        if (countByClubTypeAndUser != 0L && club.type != ClubType.EDITORIAL)
                throw DuplicateClubTypeApplicantException()

        val applicant = applicantSaveUtil.saveApplicant(club, user)
        messageSendUtil.send(club.user, "????????? ?????? ??????", "${user.nickname}?????? ${club.name}??? ??????????????????.", SendType.CLUB)
        return ClubApplyDto(applicant)
    }

}