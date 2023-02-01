package com.msg.gcms.domain.user.utils

import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto

interface UserConverter {
    fun toResponseDto(dto: UserDto, clubListResponseDto: List<ClubListResponseDto>): UserResponseDto
}