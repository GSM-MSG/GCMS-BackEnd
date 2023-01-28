package com.msg.gcms.domain.club.presentation.data.dto

import com.msg.gcms.domain.club.enums.ClubType

data class ClubListDto(
    val id: Long,
    val type: ClubType,
    val title: String,
    val bannerUrl: String
)
