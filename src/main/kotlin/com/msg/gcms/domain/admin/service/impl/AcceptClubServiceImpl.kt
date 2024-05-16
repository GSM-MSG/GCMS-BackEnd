package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.service.AcceptClubService
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.global.event.SendMessageEvent
import com.msg.gcms.global.fcm.enums.SendType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class AcceptClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : AcceptClubService {
    override fun execute(clubId: Long) {
        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()

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
            isOpened = true,
            clubStatus = ClubStatus.CREATED
        )

        clubRepository.save(newClub)

        applicationEventPublisher.publishEvent(
            SendMessageEvent(
                user = club.user,
                title = "동아리 개설 승인",
                content = "동아리 개설 신청이 승인되었어요.",
                type = SendType.CLUB
            )
        )
    }
}