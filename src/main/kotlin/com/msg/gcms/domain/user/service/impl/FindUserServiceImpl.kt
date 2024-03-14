package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil

@ServiceWithReadOnlyTransaction
class FindUserServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userConverter: UserConverter
) : FindUserService {
    override fun execute(): UserDto {
        val user = userUtil.fetchCurrentUser()
        val clubListDto = getClubListByUser(user)
            .map { userConverter.toDto(it) }
        return userConverter.toDto(user,clubListDto)
    }
    private fun getClubListByUser(user: User): List<Club> {
        val clubList = clubRepository.findAllByClubStatusAndUser(ClubStatus.CREATED, user)
        val memberClubList = clubMemberRepository.findByUser(user)
            .map { it.club }
            .filter { it.clubStatus == ClubStatus.CREATED }
        return clubList + memberClubList
    }

}