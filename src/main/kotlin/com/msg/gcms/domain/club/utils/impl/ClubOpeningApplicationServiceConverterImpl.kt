package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.ClubOpeningApplication
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto
import com.msg.gcms.domain.club.presentation.data.request.CreateClubOpeningApplicationRequest
import com.msg.gcms.domain.club.utils.ClubOpeningApplicationServiceConverter
import org.springframework.stereotype.Component

@Component
class ClubOpeningApplicationServiceConverterImpl : ClubOpeningApplicationServiceConverter {
    override fun toEntity(clubOpeningApplication: ClubOpeningApplication, club: Club): Club =
        Club(
            id = 0,
            type = club.type,
            name = club.name,
            content = club.content,
            bannerImg = club.bannerImg,
            contact = club.contact,
            notionLink = club.notionLink,
            teacher = club.teacher,
            activityImg = club.activityImg,
            clubMember = club.clubMember,
            user = club.user,
            applicant = club.applicant,
            isOpened = club.isOpened,
            clubStatus = ClubStatus.PENDING,
            clubOpeningApplication = clubOpeningApplication
        )

    override fun toDto(createClubOpeningApplicationRequest: CreateClubOpeningApplicationRequest): ClubOpeningApplicationDto =
        ClubOpeningApplicationDto(
            subject = createClubOpeningApplicationRequest.subject,
            reason = createClubOpeningApplicationRequest.reason,
            target = createClubOpeningApplicationRequest.target,
            effect = createClubOpeningApplicationRequest.effect
        )
}