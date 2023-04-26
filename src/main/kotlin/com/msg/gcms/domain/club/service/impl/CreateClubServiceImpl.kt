package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.admin.exception.NotAceessAdminException
import com.msg.gcms.domain.applicant.exception.AlreadyClubMemberException
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
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
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.webhook.util.SendDiscordMessageUtil
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateClubServiceImpl(
    private val userUtil: UserUtil,
    private val saveClubUtil: SaveClubUtil,
    private val sendDiscordMessageUtil: SendDiscordMessageUtil,
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
        sendDiscordMessageUtil(clubDto)
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

    private fun sendDiscordMessageUtil(clubDto: ClubDto){
        sendDiscordMessageUtil.execute(clubDto.name,clubDto.type,clubDto.bannerImg)
    }
}