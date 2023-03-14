package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.FindAllStatisticsDto
import com.msg.gcms.domain.admin.service.FindAllStatisticsService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
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
    companion object {
        var duplicateClubMemberList: MutableSet<String> = mutableSetOf()
        var duplicateHeadUserList: MutableSet<String> = mutableSetOf()
    }
    override fun execute(clubType: ClubType): FindAllStatisticsDto {
        var clubMemberCount: Int = 0
        val userTotalCount: Int = userRepository.findAllByRoles(Role.ROLE_STUDENT).count()
        clubRepository.findByTypeAndClubStatus(clubType, ClubStatus.CREATED)
            .forEach { club ->
                if(club.user.roles[0] == Role.ROLE_ADMIN) {
                    return@forEach
                }
                clubMemberCount += getClubMemberCount(club, clubType, duplicateClubMemberList)
                if(club.user.id.toString() !in duplicateHeadUserList)
                    duplicateHeadUserList.add(club.user.id.toString())
            }
        clubMemberCount += duplicateHeadUserList.count()
        return adminConverter.toDto(userTotalCount, clubMemberCount)
    }

    private fun getClubMemberCount(club: Club, clubType: ClubType, duplicateClubMemberList: MutableSet<String>): Int {
        if(clubType == ClubType.EDITORIAL) {
            if(club.user.id.toString() !in duplicateHeadUserList)
                duplicateHeadUserList.add(club.user.id.toString())
            var clubCount: Int = 0
            club.clubMember
                .forEach {
                    if(it.user.roles[0] == Role.ROLE_ADMIN) {
                        return@forEach
                    }
                    if(it.user.id.toString() !in duplicateClubMemberList) {
                        duplicateClubMemberList.add(it.user.id.toString())
                        clubCount++
                    }
                }
            return clubCount

        }
        return club.clubMember.count()
    }
}