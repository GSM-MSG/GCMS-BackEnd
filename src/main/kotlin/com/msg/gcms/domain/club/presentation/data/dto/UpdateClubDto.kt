package com.msg.gcms.domain.club.presentation.data.dto

import java.util.*

data class UpdateClubDto (
    val name: String,
    val content: String,
    val bannerImg: String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val activityImgs: List<String>,
    val member: List<UUID>
)