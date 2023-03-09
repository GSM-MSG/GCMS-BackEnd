package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.NotApplicantException
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.CancelApplicationService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
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
    private val messageSendUtil: MessageSendUtil,
) : CancelApplicationService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val user = userUtil.fetchCurrentUser()
        val applicant = club.applicant
            .find { it.user == user }
            ?: throw NotApplicantException()
        applicantRepository.delete(applicant)
        messageSendUtil.send(club.user, "동아리 신청 취소", "${user.nickname}님이 ${club.name}에 신청 취소했습니다.", SendType.CLUB)
    }
}