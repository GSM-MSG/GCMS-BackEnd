package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.service.CloseClubService
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CloseClubServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository
) : CloseClubService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findById(clubId)
            .orElseThrow { throw ClubNotFoundException() }
        val user = userUtil.fetchCurrentUser()
        if (club.user != user)
            throw HeadNotSameException()
        val newClub = Club(
            id = club.id,
            activityImg = club.activityImg,
            applicant = club.applicant,
            bannerImg = club.bannerImg,
            clubMember = club.clubMember,
            contact = club.contact,
            content = club.content,
            name = club.name,
            notionLink = club.notionLink,
            teacher = club.teacher,
            type = club.type,
            user = club.user,
            isOpened = false
        )
        clubRepository.save(newClub)
    }

}