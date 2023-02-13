package com.msg.gcms.domain.applicant.service.impl

import com.msg.gcms.domain.applicant.presentation.data.dto.ApplicantListDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.ApplicantListService
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.enums.Scope
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplicantListServiceImpl(
    private val applicantRepository: ApplicantRepository,
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val applicantConverter: ApplicantConverter
) : ApplicantListService {
    override fun execute(clubId: Long): ApplicantListDto {
        val user = userUtil.fetchCurrentUser()
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val scope = getUserScope(user, club)
        val applicantList = applicantRepository.findAllByClub(club)
            .map { applicantConverter.toDto(it.user) }
        return applicantConverter.toDto(scope, applicantList)
    }

    private fun getUserScope(user: User, club: Club): Scope =
        if(user.id == club.user.id) {
            Scope.HEAD
        } else if(clubMemberRepository.existsByUserAndClub(user, club)) {
            Scope.MEMBER
        } else {
            throw NotClubMemberException()
        }
}