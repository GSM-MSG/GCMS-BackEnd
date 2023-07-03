package com.msg.gcms.domain.club.presentation.data.request

import javax.validation.constraints.NotBlank

data class CreateOpeningApplicationRequest(
    @field:NotBlank
    val field: String,

    @field:NotBlank
    val subject: String,

    @field:NotBlank
    val reason: String,

    @field:NotBlank
    val target: String,

    @field:NotBlank
    val effect: String
)