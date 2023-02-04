package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotExitException
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.domain.club.service.ExitClubService
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ExitClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userUtil: UserUtil
) : ExitClubService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val user = userUtil.fetchCurrentUser()
        if (club.user == user)
            throw ClubNotExitException()
        val clubMember = club.clubMember
            .find { it.user == user }
            ?: throw NotClubMemberException()
        clubMemberRepository.delete(clubMember)
    }
}