package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.OpenClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.UpdateClubStatusUtil
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class OpenClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val updateClubStatusUtil: UpdateClubStatusUtil,
    private val clubConverter: ClubConverter,
    private val userUtil: UserUtil
) : OpenClubService {
    override fun execute(clubId: Long): ClubStatusDto {
        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()
        val user = userUtil.fetchCurrentUser()
        if (user != club.user && !user.roles.contains(Role.ROLE_ADMIN))
            throw HeadNotSameException()
        val newClub = updateClubStatusUtil.changeIsOpened(club, true)
        return clubConverter.toStatusDto(newClub)
    }
}