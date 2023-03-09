package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.exception.NotApplicantException
import com.msg.gcms.domain.applicant.presentation.data.dto.RejectDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.RejectApplicantService
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(rollbackFor = [Exception::class])
class RejectApplicantServiceImpl(
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
    private val applicantRepository: ApplicantRepository,
    private val userUtil: UserUtil,
    private val messageSendUtil: MessageSendUtil
) : RejectApplicantService {
    override fun execute(clubId: Long, rejectDto: RejectDto) {
        val clubInfo: Club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val headUserInfo = userUtil.fetchCurrentUser()

        if (clubInfo.user != headUserInfo)
            throw HeadNotSameException()

        val applicantUser: User =
            userRepository.findByIdOrNull(UUID.fromString(rejectDto.uuid)) ?: throw UserNotFoundException()

        val applicant = clubInfo.applicant
            .find { it.user == applicantUser }
            ?: throw NotApplicantException()

        applicantRepository.delete(applicant)

        messageSendUtil.send(applicantUser, "동아리 신청 거절", "${clubInfo.name}에 거절되셨습니다.", SendType.CLUB)
    }
}