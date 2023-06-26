package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.OpeningApplication
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto
import com.msg.gcms.domain.club.presentation.data.request.CreateOpeningApplicationRequest
import com.msg.gcms.domain.club.utils.OpeningApplicationConverter
import org.springframework.stereotype.Component

@Component
class OpeningApplicationConverterImpl : OpeningApplicationConverter {
    override fun toEntity(openingApplication: OpeningApplication, club: Club): Club =
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
            openingApplication = openingApplication
        )

    override fun toDto(createOpeningApplicationRequest: CreateOpeningApplicationRequest): ClubOpeningApplicationDto =
        ClubOpeningApplicationDto(
            subject = createOpeningApplicationRequest.subject,
            reason = createOpeningApplicationRequest.reason,
            target = createOpeningApplicationRequest.target,
            effect = createOpeningApplicationRequest.effect
        )
}