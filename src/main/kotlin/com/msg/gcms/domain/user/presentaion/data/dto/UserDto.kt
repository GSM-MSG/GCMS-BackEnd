package com.msg.gcms.domain.user.presentaion.data.dto

import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import java.util.UUID

data class UserDto (
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Long,
    val classNum: Long,
    val number: Long,
    val profileImg: String,
    val clubs: List<ClubListDto>
)