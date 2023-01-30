package com.msg.gcms.domain.clubMember.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.exception.ClubMemberNonExistentException
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindClubMemberListService(
    private val clubMemberRepository: ClubMemberRepository,
    private val clubRepository: ClubRepository,
    private val clubMemberConverter: ClubMemberConverter,
    private val userUtil: UserUtil
) {

    fun execute(clubId: Long): ClubMemberListDto {
        val user = userUtil.fetchCurrentUser()
        val club: Club = clubRepository.findById(clubId)
            .orElseThrow { ClubNotFoundException() }
        val clubMemberList: List<ClubMemberDto> = clubMemberRepository.findAllByClub(club)
            .map { clubMemberConverter.toDto(it) }
        val scope: MemberScope = getScopeFromClubMember(user, club)
        return clubMemberConverter.toListDto(scope, clubMemberList)
    }

    private fun getScopeFromClubMember(user: User, club: Club): MemberScope {
        val existsClub: ClubMember? = club.clubMember
            .find { it.user.id == user.id }
        return existsClub?.scope ?: throw ClubMemberNonExistentException()
    }
}