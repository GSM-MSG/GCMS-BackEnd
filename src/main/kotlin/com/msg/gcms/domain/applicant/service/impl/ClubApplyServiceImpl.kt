package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.AlreadyClubMemberException
import com.msg.gcms.domain.applicant.exception.DuplicateClubTypeApplicantException
import com.msg.gcms.domain.applicant.exception.SameClubApplicantException
import com.msg.gcms.domain.applicant.presentation.data.dto.ClubApplyDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.ClubApplyService
import com.msg.gcms.domain.applicant.util.ApplicantSaveUtil
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.ClubNotOpeningException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.event.SendMessageEvent
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.UserUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class ClubApplyServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val applicantSaveUtil: ApplicantSaveUtil,
    private val applicantRepository: ApplicantRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : ClubApplyService {
    override fun execute(clubId: Long): ClubApplyDto {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        if(!club.isOpened)
            throw ClubNotOpeningException()
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

        applicationEventPublisher.publishEvent(
            SendMessageEvent(
                user = club.user,
                title = "동아리 신청 요청",
                content = "${user.nickname}님이 ${club.name}에 신청했습니다.",
                type = SendType.CLUB
            )
        )

        return ClubApplyDto(applicant)
    }

}