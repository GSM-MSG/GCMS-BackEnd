package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.RejectClubService
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class RejectClubServiceImpl(
    private val clubRepository: ClubRepository
) : RejectClubService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()

        clubRepository.delete(club)
    }
}