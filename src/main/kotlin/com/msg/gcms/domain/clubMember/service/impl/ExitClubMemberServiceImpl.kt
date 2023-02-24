package com.msg.gcms.domain.clubMember.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.exception.ClubMemberExitOneSelfException
import com.msg.gcms.domain.clubMember.exception.ClubMemberReleaseNotFoundException
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberExitDto
import com.msg.gcms.domain.clubMember.service.ExitClubMemberService
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ExitClubMemberServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository
) : ExitClubMemberService {
    override fun execute(clubMemberExitDto: ClubMemberExitDto) {
        val user = userUtil.fetchCurrentUser()
        if(user.id.toString() == clubMemberExitDto.uuid) {
            throw ClubMemberExitOneSelfException()
        }
        val club = clubRepository.findByIdOrNull(clubMemberExitDto.clubId)
            ?: throw ClubNotFoundException()
        if(club.user != user) {
            throw HeadNotSameException()
        }
        val memberRelease: ClubMember = getClubMemberToRelease(club, clubMemberExitDto.uuid)
        clubMemberRepository.delete(memberRelease)
    }

    private fun getClubMemberToRelease(club: Club, uuid: String): ClubMember =
        clubMemberRepository.findByClub(club)
            .find { it.user.id.toString() == uuid }
            ?: throw ClubMemberReleaseNotFoundException()
}