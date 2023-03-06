package com.msg.gcms.domain.admin.util

import com.msg.gcms.domain.admin.presentation.data.dto.ClubInfoDto
import com.msg.gcms.domain.admin.presentation.data.dto.FindAllUserListDto
import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto
import com.msg.gcms.domain.admin.presentation.data.dto.UserDetailInfoDto
import com.msg.gcms.domain.admin.presentation.data.response.FindAllUserListResponse
import com.msg.gcms.domain.admin.presentation.data.response.PendingClubResponse
import com.msg.gcms.domain.admin.presentation.data.response.UserDetailInfoResponse
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.user.domain.entity.User

interface AdminConverter {
    fun toDto(club: Club): PendingClubDto
    fun toResponse(dto: PendingClubDto): PendingClubResponse

    fun toDto(user: User): FindAllUserListDto

    fun toResponse(dto: FindAllUserListDto): FindAllUserListResponse

    fun toDto(user: User, major: ClubInfoDto, freedom: ClubInfoDto, editorial: List<ClubInfoDto>): UserDetailInfoDto

    fun toResponse(dto: UserDetailInfoDto): UserDetailInfoResponse

    fun toClubInfoDto(club: Club): ClubInfoDto
}