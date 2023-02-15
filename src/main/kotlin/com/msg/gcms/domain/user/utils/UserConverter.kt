package com.msg.gcms.domain.user.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.presentaion.data.dto.*
import com.msg.gcms.domain.user.presentaion.data.request.UpdateProfileImgRequestDto
import com.msg.gcms.domain.user.presentaion.data.response.SearchUserResponseDto
import com.msg.gcms.domain.user.presentaion.data.response.UserProfileResponseDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto

interface UserConverter {
    fun toUserProfileDto(user: User): UserProfileDto
    fun toDto(user: User, dto: List<UserDto.ClubDto>): UserDto
    fun toDto(club: Club): UserDto.ClubDto
    fun toDto(type: ClubType, name: String): SearchRequirementDto
    fun toDto(user: User): SearchUserDto
    fun toDto(requestDto: UpdateProfileImgRequestDto): ProfileImgDto
    fun toResponseDto(dto: SearchUserDto): SearchUserResponseDto
    fun toResponseDto(dto: UserDto.ClubDto): UserResponseDto.ClubResponseDto
    fun toResponseDto(dto: UserProfileDto): UserProfileResponseDto
    fun toResponseDto(dto: UserDto, responseDto: List<UserResponseDto.ClubResponseDto>): UserResponseDto
}