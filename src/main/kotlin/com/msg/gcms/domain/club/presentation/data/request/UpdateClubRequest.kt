package com.msg.gcms.domain.club.presentation.data.request

import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateClubRequest (
    @field:NotBlank
    @field:Size(min = 1, max = 25)
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