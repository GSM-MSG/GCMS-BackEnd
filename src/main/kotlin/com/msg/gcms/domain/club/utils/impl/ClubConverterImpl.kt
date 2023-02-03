package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class ClubConverterImpl : ClubConverter {
    override fun toDto(createClubRequest: CreateClubRequest): ClubDto =
        ClubDto(
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
            id = 0,
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

    override fun toDto(type: ClubType): ClubTypeDto =
        ClubTypeDto(clubType = type)

    override fun toDto(club: Club): ClubListDto =
        ClubListDto(id = club.id, type = club.type, name = club.name, bannerImg = club.bannerImg )

    override fun toResponseDto(dto: ClubListDto): ClubListResponseDto =
        ClubListResponseDto(id = dto.id, type = dto.type, name = dto.name, bannerImg = dto.bannerImg)

    override fun toStatusDto(club: Club): ClubStatusDto =
        ClubStatusDto(club.isOpened)

    override fun toDto(updateClubRequest: UpdateClubRequest): ClubDto =
        ClubDto(
            type = updateClubRequest.type,
            name = updateClubRequest.name,
            content = updateClubRequest.content,
            bannerImg = updateClubRequest.bannerImg,
            contact = updateClubRequest.contact,
            notionLink = updateClubRequest.notionLink,
            teacher = updateClubRequest.teacher,
            activityImgs = updateClubRequest.activityImgs,
            member = updateClubRequest.member
        )
}