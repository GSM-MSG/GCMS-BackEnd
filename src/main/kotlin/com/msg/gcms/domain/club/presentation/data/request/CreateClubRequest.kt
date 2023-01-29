package com.msg.gcms.domain.club.presentation.data.request

import com.msg.gcms.domain.club.enums.ClubType
import java.util.*

data class CreateClubRequest(
    val type: ClubType,
    val name: String,
    val content: String,
    val bannerImg: String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val activityImgs: List<String>,
    val member: List<UUID>,
)