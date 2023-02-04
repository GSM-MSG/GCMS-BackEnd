package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.applicant.domain.repository.ApplicantRepository
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ActivityImgRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.enums.Scope
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.presentation.data.dto.DetailClubDto
import com.msg.gcms.domain.club.service.DetailClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DetailClubServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val applicantRepository: ApplicantRepository,
    private val activityImgRepository: ActivityImgRepository,
    private val clubConverter: ClubConverter
) : DetailClubService {
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun execute(clubId: Long): DetailClubDto {
        val user = userUtil.fetchCurrentUser()
        val club = clubRepository.findById(clubId)
            .orElseThrow { throw ClubNotFoundException() }
        val clubMember = clubMemberRepository.findAllByClub(club)
            .map { clubConverter.toDto(it.user) }
        val activityImgs = activityImgRepository.findAllByClub(club)
            .map { it.image }
        val scope = getScope(user, club)
        val isApplied = applicantRepository.existsByUserAndClub(user, club)

        return clubConverter.toDto(club, clubMember, activityImgs, scope, isApplied)

    }
    private fun getScope(user: User, club: Club): Scope {
        return if(user.id == club.user.id) {
            Scope.HEAD
        } else if(clubMemberRepository.existsByUserAndClub(user, club)) {
            Scope.MEMBER
        } else if(club.type != ClubType.EDITORIAL && checkScopeIsOther(user, club.type)) {
            Scope.OTHER
        } else {
            Scope.USER
        }
    }
    private fun checkScopeIsOther(user: User, type: ClubType): Boolean {
        val result = userRepository.findUserJoin(type, user)
        print(result)
        return if(!result) {
            clubRepository.existsByUserAndType(user, type)
        } else {
            true
        }
    }

}