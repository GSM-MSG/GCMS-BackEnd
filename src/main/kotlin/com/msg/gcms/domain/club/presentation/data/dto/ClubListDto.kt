package com.msg.gcms.domain.club.presentation.data.dto

import com.msg.gcms.domain.club.domain.entity.enums.ClubType

data class ClubListDto(
    val id: Long,
    val type: ClubType,
    val name: String,
    val bannerImg: String
)
