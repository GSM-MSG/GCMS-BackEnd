package com.msg.gcms.domain.auth.presentation.data.response

import java.time.ZonedDateTime

data class SignInResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val accessExp: ZonedDateTime,
    val refreshExp: ZonedDateTime
)
