package com.msg.gcms.domain.club.presentation.data.response

import com.msg.gcms.domain.club.enums.ClubType

data class ClubListResponseDto(
    val id: Long,
    val type: ClubType,
    val name: String,
    val bannerUrl: String
)
