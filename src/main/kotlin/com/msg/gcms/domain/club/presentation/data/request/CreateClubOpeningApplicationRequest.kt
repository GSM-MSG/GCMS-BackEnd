package com.msg.gcms.domain.club.presentation.data.request

data class CreateClubOpeningApplicationRequest(
    val subject: String,

    val reason: String,

    val target: String,

    val effect: String
)