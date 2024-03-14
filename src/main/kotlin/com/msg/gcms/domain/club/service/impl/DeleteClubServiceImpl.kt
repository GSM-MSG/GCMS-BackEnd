package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.service.DeleteClubService
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class DeleteClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val userUtil: UserUtil
) : DeleteClubService{
    override fun execute(clubId: Long) {
        val club = (clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException())
        val user = userUtil.fetchCurrentUser()
        if (club.user != user && !user.roles.contains(Role.ROLE_ADMIN))
            throw HeadNotSameException()
        clubRepository.delete(club)
    }
}