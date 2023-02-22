package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubAlreadyExistsException
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
    private val clubRepository: ClubRepository,
    private val clubConverter: ClubConverter
) : CreateClubService {
    override fun execute(clubDto: ClubDto) {
        if (clubRepository.existsByNameAndType(clubDto.name, clubDto.type))
            throw ClubAlreadyExistsException()
        val currentUser = userUtil.fetchCurrentUser()
        val club = clubConverter.toEntity(clubDto, currentUser)
        saveClubUtil.saveClub(club, clubDto.activityImgs, clubDto.member)
    }
}