package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.exception.ClubStatusNotPendingException
import com.msg.gcms.domain.admin.service.RejectClubService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class RejectClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val messageSendUtil: MessageSendUtil
) : RejectClubService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()

        if(club.clubStatus != ClubStatus.PENDING) {
            throw ClubStatusNotPendingException()
        }

        clubRepository.delete(club)
        messageSendUtil.send(club.user, "동아리 개설 거절", "동아리 개설 신청이 거절되었어요.", SendType.CLUB)
    }
}