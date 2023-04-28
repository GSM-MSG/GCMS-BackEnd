package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.applicant.exception.AlreadyClubMemberException
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.exception.AlreadyClubApplicantException
import com.msg.gcms.domain.club.exception.AlreadyClubHeadException
import com.msg.gcms.domain.club.exception.ClubAlreadyExistsException
import com.msg.gcms.domain.club.exception.ClubAlreadyPendingException
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.service.CreateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.SaveClubUtil
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.webhook.util.DiscordUtil
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateClubServiceImpl(
    private val userUtil: UserUtil,
    private val saveClubUtil: SaveClubUtil,
    private val discordUtil: DiscordUtil,
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val applicantRepository: ApplicantRepository,
    private val clubConverter: ClubConverter
) : CreateClubService {
    override fun execute(clubDto: ClubDto) {
        if (clubRepository.existsByNameAndType(clubDto.name, clubDto.type))
            throw ClubAlreadyExistsException()
        val currentUser = userUtil.fetchCurrentUser()
        if(clubRepository.existsByTypeAndUserAndClubStatus(clubDto.type, currentUser, ClubStatus.PENDING))
            throw ClubAlreadyPendingException()
        val club = clubConverter.toEntity(clubDto, currentUser)
        checkAlreadyHead(currentUser, clubDto)
        checkAlreadyClubMember(currentUser, clubDto)
        checkAlreadyApplicant(currentUser, clubDto)
        saveClubUtil.saveClub(club, clubDto.activityImgs, clubDto.member)
        sendDiscordMessage(clubDto)
    }

    private fun checkAlreadyHead(
        currentUser: User,
        clubDto: ClubDto
    ) {
        if (clubDto.type != ClubType.EDITORIAL && clubRepository.existsByUserAndType(currentUser, clubDto.type))
            throw AlreadyClubHeadException()
    }

    private fun checkAlreadyClubMember(
        currentUser: User,
        clubDto: ClubDto
    ){
        if (clubDto.type != ClubType.EDITORIAL && clubMemberRepository.findByUser(currentUser).any { it.club.type == clubDto.type })
            throw AlreadyClubMemberException()
    }

    private fun checkAlreadyApplicant(currentUser: User, clubDto: ClubDto){
        if (clubDto.type != ClubType.EDITORIAL && applicantRepository.findAllByUser(currentUser).any { it.club.type == clubDto.type })
            throw AlreadyClubApplicantException()
    }

    private fun sendDiscordMessage(clubDto: ClubDto){
        discordUtil.createClubMessage(clubDto.name, clubDto.type, clubDto.bannerImg)
    }
}