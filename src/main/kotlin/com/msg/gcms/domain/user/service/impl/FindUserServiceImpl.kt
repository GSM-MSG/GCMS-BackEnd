package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FindUserServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val clubConverter: ClubConverter,
    private val userConverter: UserConverter
) : FindUserService {
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun execute(): UserDto {
        val user = userUtil.fetchCurrentUser()
        val clubListDto = getClubListWithUser(user)
            .map { clubConverter.toDto(it) }
        return userConverter.toDto(user,clubListDto)
    }
    private fun getClubListWithUser(user: User): List<Club> {
        val clubList = clubRepository.findByUser(user)
        val memberClubList = clubMemberRepository.findByUser(user).map { it.club }
        return clubList + memberClubList
    }

}