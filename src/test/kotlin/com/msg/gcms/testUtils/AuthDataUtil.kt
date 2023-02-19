package com.msg.gcms.testUtils

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import java.time.ZonedDateTime
import java.util.*

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

    fun entity(userId: UUID) =
        RefreshToken(
            userId = userId,
            token = "thisIsRefreshToken"
        )


}