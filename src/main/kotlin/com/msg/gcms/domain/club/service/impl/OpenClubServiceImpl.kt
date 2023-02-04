package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.OpenClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.UpdateClubStatusUtil
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class OpenClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val updateClubStatusUtil: UpdateClubStatusUtil,
    private val clubConverter: ClubConverter,
    private val userUtil: UserUtil
) : OpenClubService {
    override fun execute(clubId: Long): ClubStatusDto {
        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()
        val user = userUtil.fetchCurrentUser()
        if(club.user != user) {
            throw HeadNotSameException()
        }
        updateClubStatusUtil.changeIsOpened(club, true)
        return clubConverter.toStatusDto(club)
    }
}