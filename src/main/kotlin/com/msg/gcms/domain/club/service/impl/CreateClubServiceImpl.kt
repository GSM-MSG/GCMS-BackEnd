package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.service.CreateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.SaveClubUtil
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateClubServiceImpl(
    private val userUtil: UserUtil,
    private val saveClubUtil: SaveClubUtil,
    private val clubConverter: ClubConverter
) : CreateClubService {
    override fun execute(clubDto: ClubDto) {
        val currentUser = userUtil.fetchCurrentUser()
        val club = clubConverter.toEntity(clubDto, currentUser)
        saveClubUtil.saveClub(club, clubDto)
    }
}