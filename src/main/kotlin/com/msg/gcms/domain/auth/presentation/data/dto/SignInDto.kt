package com.msg.gcms.domain.auth.presentation.data.dto

data class SignInDto(
    val code: String,
    val token: String?,
)