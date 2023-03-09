package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.FindAllStatisticsDto
import com.msg.gcms.domain.admin.service.FindAllStatisticsService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindAllStatisticsServiceImpl(
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
    private val adminConverter: AdminConverter
) : FindAllStatisticsService {
    override fun execute(clubType: ClubType): FindAllStatisticsDto {
        var applicantCount: Int = 0
        var duplicateHeadUserList: MutableSet<String> = mutableSetOf()
        val userTotalCount: Int = userRepository.findAllByRoles(Role.ROLE_STUDENT).count()
        clubRepository.findByType(clubType)
            .forEach { club ->
                applicantCount += getApplicantCount(club)
                if(club.user.id.toString() !in duplicateHeadUserList)
                    duplicateHeadUserList.add(club.user.id.toString())
            }
        applicantCount += duplicateHeadUserList.count()
        return adminConverter.toDto(userTotalCount, applicantCount)
    }

    private fun getApplicantCount(club: Club): Int {
        return club.clubMember.count()
    }
}