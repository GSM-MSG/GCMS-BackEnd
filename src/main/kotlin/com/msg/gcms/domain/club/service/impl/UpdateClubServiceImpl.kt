package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ActivityImgRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.presentation.data.dto.UpdateClubDto
import com.msg.gcms.domain.club.service.UpdateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.SaveClubUtil
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil

@ServiceWithTransaction
class UpdateClubServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val activityImgRepository: ActivityImgRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val saveClubUtil: SaveClubUtil,
    private val clubConverter: ClubConverter
) : UpdateClubService {
    override fun execute(id: Long, clubDto: UpdateClubDto) {
        val foundClub = clubRepository.findById(id)
            .orElseThrow { throw ClubNotFoundException() }
        val user = userUtil.fetchCurrentUser()
        if (user != foundClub.user && !user.roles.contains(Role.ROLE_ADMIN))
            throw HeadNotSameException()
        val club = clubConverter.toEntity(id, clubDto, foundClub.user, foundClub.type, foundClub.clubStatus)
        activityImgRepository.deleteByClub(foundClub)
        clubMemberRepository.deleteByClub(foundClub)
        saveClubUtil.saveClub(club, clubDto.activityImgs, clubDto.member)
    }
}