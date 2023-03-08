package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.FindAllStatisticsDto
import com.msg.gcms.domain.admin.service.FindAllStatisticsService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindAllStatisticsServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userRepository: UserRepository,
    private val adminConverter: AdminConverter
) : FindAllStatisticsService {
    override fun execute(clubType: ClubType): FindAllStatisticsDto {
        var applicantCount: Int = 0
        val userTotalCount: Int = userRepository.findAllByRoles(Role.ROLE_STUDENT).count()
        clubRepository.findByType(clubType)
            .forEach { applicantCount += getApplicantCount(it) }
        return adminConverter.toDto(userTotalCount, applicantCount)
    }

    private fun getApplicantCount(club: Club): Int {
        var clubHeadCount = 0
        if(club.user != null)
            clubHeadCount = 1
        val clubMemberCount = club.clubMember.count()
        return clubHeadCount + clubMemberCount
    }
}