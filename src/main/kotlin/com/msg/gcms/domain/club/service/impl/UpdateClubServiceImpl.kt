package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.repository.ActivityImgRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.service.UpdateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.SaveClubUtil
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateClubServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val activityImgRepository: ActivityImgRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val saveClubUtil: SaveClubUtil,
    private val clubConverter: ClubConverter
) : UpdateClubService {
    override fun execute(id: Long, clubDto: ClubDto) {
        val foundClub = clubRepository.findById(id)
            .orElseThrow { throw RuntimeException() }
        val user = userUtil.fetchCurrentUser()
        if (foundClub.user != user)
            throw RuntimeException()
        val club = clubConverter.toEntity(clubDto, user)
        club.id = id
        activityImgRepository.deleteByClub(club)
        clubMemberRepository.deleteByClub(club)
        saveClubUtil.saveClub(club, clubDto)
    }
}