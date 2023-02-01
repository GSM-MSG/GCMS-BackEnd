package com.msg.gcms.domain.user.utils.impl

import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto
import com.msg.gcms.domain.user.utils.UserConverter
import org.springframework.stereotype.Component

@Component
class UserConverterImpl : UserConverter {
    override fun toResponseDto(dto: UserDto, clubResponseDto: List<ClubListResponseDto>): UserResponseDto {
        TODO("Not yet implemented")
    }
}