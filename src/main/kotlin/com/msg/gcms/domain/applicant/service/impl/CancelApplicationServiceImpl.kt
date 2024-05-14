package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.NotApplicantException
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.CancelApplicationService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.event.SendMessageEvent
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.UserUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class CancelApplicationServiceImpl(
    private val clubRepository: ClubRepository,
    private val applicantRepository: ApplicantRepository,
    private val userUtil: UserUtil,
    private val applicationEventPublisher: ApplicationEventPublisher
) : CancelApplicationService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val user = userUtil.fetchCurrentUser()
        val applicant = club.applicant
            .find { it.user == user }
            ?: throw NotApplicantException()
        applicantRepository.delete(applicant)

        applicationEventPublisher.publishEvent(
            SendMessageEvent(
                user = club.user,
                title = "동아리 신청 취소",
                content = "${user.nickname}님이 ${club.name}에 신청 취소했습니다.",
                type = SendType.CLUB
            )
        )
    }
}