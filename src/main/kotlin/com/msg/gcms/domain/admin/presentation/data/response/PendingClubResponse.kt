package com.msg.gcms.domain.admin.presentation.data.response

import com.msg.gcms.domain.club.enums.ClubType

data class PendingClubResponse (
    val id: Long,
    val bannerImg: String,
    val name: String,
    val type: ClubType,
    val content: String
)