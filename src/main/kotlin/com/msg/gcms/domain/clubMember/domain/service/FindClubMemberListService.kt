package com.msg.gcms.domain.clubMember.domain.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.exception.ClubNotFoundException
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.exception.ClubMemberNonExistentException
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.domain.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class FindClubMemberListService(
    private val clubMemberRepository: ClubMemberRepository,
    private val clubRepository: ClubRepository,
    private val clubMemberConverter: ClubMemberConverter,
    private val userUtil: UserUtil
) {

    fun execute(clubId: Long): ClubMemberListDto {
        val user = userUtil.fetchCurrentUser()
        val club: Club = clubRepository.findById(clubId).orElseThrow { ClubNotFoundException() }
        val clubMemberList: List<ClubMemberDto> =
            clubMemberRepository.findAllByClub(club).map { clubMemberConverter.toDto(it) }
        val scope: MemberScope = checkClubMemberScope(user, club)
        return clubMemberConverter.toListDto(scope, clubMemberList)
    }

    private fun checkClubMemberScope(user: User, club: Club): MemberScope {
        val existsClub: ClubMember? = club.clubMember.find { it.user == user }
        return existsClub?.scope ?: throw ClubMemberNonExistentException()
    }
}