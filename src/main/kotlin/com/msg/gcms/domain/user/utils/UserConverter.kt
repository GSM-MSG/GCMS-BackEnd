package com.msg.gcms.domain.user.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.presentaion.data.dto.UserDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto

interface UserConverter {
    fun toDto(user: User, dto: List<UserDto.ClubDto>): UserDto
    fun toDto(club: Club): UserDto.ClubDto
    fun toResponseDto(dto: UserDto, responseDto: List<ClubListResponseDto>): UserResponseDto
}