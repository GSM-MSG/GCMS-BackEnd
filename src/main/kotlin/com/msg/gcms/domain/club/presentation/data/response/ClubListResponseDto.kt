package com.msg.gcms.domain.club.presentation.data.response

import com.msg.gcms.domain.club.domain.entity.enums.ClubType

data class ClubListResponseDto(
    val id: Long,
    val type: ClubType,
    val name: String,
    val bannerImg: String
)
