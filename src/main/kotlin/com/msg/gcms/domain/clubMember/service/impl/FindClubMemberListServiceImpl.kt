package com.msg.gcms.domain.clubMember.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.exception.ClubMemberNonExistentException
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindClubMemberListServiceImpl(
    private val clubMemberRepository: ClubMemberRepository,
    private val clubRepository: ClubRepository,
    private val clubMemberConverter: ClubMemberConverter,
    private val userUtil: UserUtil,
) : FindClubMemberListService {

    override fun execute(clubId: Long): ClubMemberListDto {
        val user = userUtil.fetchCurrentUser()
        val club: Club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()
        val clubMemberList: MutableList<ClubMemberListDto.SingleClubMemberDto> =
            clubMemberRepository.findAllByClub(club)
                .map { getScopeFromClubMember(it.user, club) to it }
                .map { clubMemberConverter.toDto(it.second, it.first) }.toMutableList()
        val scope: MemberScope = getScopeFromClubMember(user, club)
        getClubHeadInfo(club)
            ?.let { clubMemberList.add(it) }
        return clubMemberConverter.toListDto(scope, clubMemberList)
    }

    private fun getScopeFromClubMember(user: User, club: Club): MemberScope {
        if (club.user.id == user.id) {
            return MemberScope.HEAD
        } else {
            if (!existsClubMember(club, user)) {
                throw ClubMemberNonExistentException()
            }
            return MemberScope.MEMBER
        }
    }

    private fun getClubHeadInfo(club: Club): ClubMemberListDto.SingleClubMemberDto =
        clubMemberConverter.toDto(club)

    private fun existsClubMember(club: Club, user: User): Boolean =
        club.clubMember
            .any { it.user == user }
}