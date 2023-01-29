package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class ClubConverterImpl : ClubConverter {
    override fun toDto(createClubRequest: CreateClubRequest): ClubDto =
        ClubDto(
            id = 0,
            type = createClubRequest.type,
            name = createClubRequest.name,
            content = createClubRequest.content,
            bannerImg = createClubRequest.bannerImg,
            contact = createClubRequest.contact,
            notionLink = createClubRequest.notionLink,
            teacher = createClubRequest.teacher,
            activityImgs = createClubRequest.activityImgs,
            member = createClubRequest.member
        )

    override fun toEntity(clubDto: ClubDto, user: User): Club =
        Club(
            id = clubDto.id,
            type = clubDto.type,
            name = clubDto.name,
            content = clubDto.content,
            bannerImg = clubDto.bannerImg,
            contact = clubDto.contact,
            notionLink = clubDto.notionLink,
            teacher = clubDto.teacher,
            activityImg = listOf(),
            clubMember = listOf(),
            user = user,
            applicant = listOf(),
            isOpened = true,
        )
}