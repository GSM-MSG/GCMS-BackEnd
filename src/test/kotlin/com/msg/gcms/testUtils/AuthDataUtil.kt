package com.msg.gcms.testUtils

import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import gauth.GAuthUserInfo
import java.time.ZonedDateTime
import java.util.UUID

object AuthDataUtil {
    fun signInRequestDto(code: String) = SignInRequestDto(
        code = code
    )

    fun signInDto(code: String) = SignInDto(
        code = code
    )

    fun signInResponseDto(
        accessToken: String,
        refreshToken: String,
        accessExp: ZonedDateTime,
        refreshExp: ZonedDateTime
    ) = SignInResponseDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessExp = accessExp,
        refreshExp = refreshExp
    )
}