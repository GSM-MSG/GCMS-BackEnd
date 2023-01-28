package com.msg.gcms.domain.clubMember.domain.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.exception.ClubNotFoundException
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.domain.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class FindClubMemberListService(
    private val clubMemberRepository: ClubMemberRepository,
    private val clubRepository: ClubRepository,
    private val clubMemberConverter: ClubMemberConverter
) {

    fun execute(clubId: Long) {
        val club: Club = clubRepository.findById(clubId).orElseThrow { ClubNotFoundException() }
        val clubMemberList: List<ClubMemberDto> = checkClubMemberScope()
            clubMemberRepository.findAllByClub(club).map { clubMemberConverter.toDto(it) }
        val scope: MemberScope = checkClubMemberScope()
        return clubMemberConverter.toListDto()
    }

    private fun checkClubMemberScope(user: User, club: Club): MemberScope {
        val existsClub: ClubMember? = user.clubMember.find { it.club == club }
        return existsClub?.scope ?: throw RuntimeException()
    }
}