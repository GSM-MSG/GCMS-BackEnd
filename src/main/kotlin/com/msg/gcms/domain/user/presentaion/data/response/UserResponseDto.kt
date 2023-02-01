package com.msg.gcms.domain.user.presentaion.data.response

import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import java.util.*

data class UserResponseDto(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Long,
    val classNum: Long,
    val number: Long,
    val profileImg: String,
    val clubs: List<ClubListResponseDto>
)
