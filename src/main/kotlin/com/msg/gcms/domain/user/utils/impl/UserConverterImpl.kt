package com.msg.gcms.domain.user.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto
import com.msg.gcms.domain.user.utils.UserConverter
import org.springframework.stereotype.Component

@Component
class UserConverterImpl : UserConverter {
    override fun toDto(user: User, dto: List<UserDto.ClubDto>): UserDto =
        UserDto(
            uuid = user.id,
            email = user.email,
            name = user.nickname,
            grade = user.grade,
            classNum = user.classNum,
            number = user.number,
            profileImg = user.profileImg,
            clubs = dto
        )

    override fun toDto(club: Club): UserDto.ClubDto =
        UserDto.ClubDto(
            id = club.id,
            type = club.type,
            name = club.name,
            bannerImg = club.bannerImg
        )

    override fun toResponseDto(dto: UserDto, responseDto: List<ClubListResponseDto>): UserResponseDto =
        UserResponseDto(
            uuid = dto.uuid,
            email = dto.email,
            name = dto.name,
            grade = dto.grade,
            classNum = dto.classNum,
            number = dto.number,
            profileImg = dto.profileImg,
            clubs = responseDto
        )
}