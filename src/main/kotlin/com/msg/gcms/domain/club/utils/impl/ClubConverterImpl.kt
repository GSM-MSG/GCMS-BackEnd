package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.enums.Scope
import com.msg.gcms.domain.club.presentation.data.dto.*
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.presentation.data.response.DetailClubResponseDto
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

    override fun toEntity(id: Long, clubDto: UpdateClubDto, user: User, clubType: ClubType): Club =
        Club(
            id = id,
            type = clubType,
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
        ClubListDto(id = club.id, type = club.type, name = club.name, bannerImg = club.bannerImg, content = club.content )

    override fun toDto(user: User): DetailClubDto.UserDto =
        DetailClubDto.UserDto(
            uuid = user.id,
            email = user.email,
            name = user.nickname,
            grade = user.grade,
            classNum = user.classNum,
            number = user.number,
            profileImg = user.profileImg
        )

    override fun toDto(
        club: Club,
        clubMemberDto: List<DetailClubDto.UserDto>,
        clubImages: List<String>,
        scope: Scope,
        isApplied: Boolean,
    ): DetailClubDto =
        DetailClubDto(
            id = club.id,
            type = club.type,
            bannerImg = club.bannerImg,
            name = club.name,
            content = club.content,
            contact = club.contact,
            notionLink = club.notionLink,
            teacher = club.teacher,
            isOpened = club.isOpened,
            activityImgs = clubImages,
            head = toDto(club.user),
            member = clubMemberDto,
            scope = scope,
            isApplied = isApplied
        )


    override fun toResponseDto(dto: ClubListDto): ClubListResponseDto =
        ClubListResponseDto(id = dto.id, type = dto.type, name = dto.name, bannerImg = dto.bannerImg, content = dto.content)

    override fun toResponseDto(dto: DetailClubDto.UserDto): DetailClubResponseDto.UserResponseDto =
        DetailClubResponseDto.UserResponseDto(
            uuid = dto.uuid,
            email = dto.email,
            name = dto.name,
            grade = dto.grade,
            classNum = dto.classNum,
            number = dto.number,
            profileImg = dto.profileImg
        )

    override fun toResponseDto(
        dto: DetailClubDto,
        memberResponseDto: List<DetailClubResponseDto.UserResponseDto>,
    ): DetailClubResponseDto =
        DetailClubResponseDto(
            id = dto.id,
            type = dto.type,
            bannerImg = dto.bannerImg,
            name = dto.name,
            content = dto.content,
            contact = dto.contact,
            teacher = dto.teacher,
            isOpened = dto.isOpened,
            notionLink = dto.notionLink,
            activityImgs = dto.activityImgs,
            head = toResponseDto(dto.head),
            member = memberResponseDto,
            scope = dto.scope,
            isApplied = dto.isApplied
        )

    override fun toStatusDto(club: Club): ClubStatusDto =
        ClubStatusDto(club.isOpened)

    override fun toDto(updateClubRequest: UpdateClubRequest): UpdateClubDto =
        UpdateClubDto(
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