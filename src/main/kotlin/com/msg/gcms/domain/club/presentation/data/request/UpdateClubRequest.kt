package com.msg.gcms.domain.club.presentation.data.request

import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UpdateClubRequest (
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val bannerImg: String,
    @field:NotBlank
    val contact: String,
    @field:NotBlank
    val notionLink: String,
    val teacher: String? = null,
    @field:NotNull
    val activityImgs: List<String>,
    @field:NotNull
    val member: List<UUID>
)