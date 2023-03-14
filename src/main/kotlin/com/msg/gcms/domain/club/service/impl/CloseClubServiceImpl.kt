package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.CloseClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.UpdateClubStatusUtil
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CloseClubServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val clubConverter: ClubConverter,
    private val updateClubStatusUtil: UpdateClubStatusUtil
) : CloseClubService {
    override fun execute(clubId: Long): ClubStatusDto {
        val club = clubRepository.findById(clubId)
            .orElseThrow { throw ClubNotFoundException() }
        val user = userUtil.fetchCurrentUser()
        if (club.user != user && user.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()
        updateClubStatusUtil.changeIsOpened(club, false)
        return clubConverter.toStatusDto(club)
    }



}