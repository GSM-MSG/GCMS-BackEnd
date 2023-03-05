package com.msg.gcms.domain.admin.presentation.data.dto

import com.msg.gcms.domain.club.enums.ClubType

data class PendingClubDto (
    val id: Long,
    val bannerImg: String,
    val name: String,
    val type: ClubType,
    val content: String
)