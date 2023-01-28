package com.msg.gcms.domain.auth.util

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.user.domain.entity.User
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class AuthConverter {

    fun toDto(signInRequestDto: SignInRequestDto): SignInDto =
        SignInDto(
            code = signInRequestDto.code
        )

    fun toResponse(
        accessToken: String,
        refreshToken: String,
        accessExp: ZonedDateTime,
        refreshExp: ZonedDateTime
    ): SignInResponseDto =
        SignInResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )

    fun toEntity(gAuthUserInfo: GAuthUserInfo): User =
        User(
            id = UUID.randomUUID(),
            email = gAuthUserInfo.email,
            nickname = gAuthUserInfo.name,
            grade = gAuthUserInfo.grade,
            classNum = gAuthUserInfo.classNum,
            number = gAuthUserInfo.num,
            profileImg = gAuthUserInfo.profileUrl,
            club = listOf(),
            applicant = listOf(),
            clubMember = listOf()
        )

    fun toEntity(userInfo: User, refreshToken: String): RefreshToken =
        RefreshToken(
            userId = userInfo.id,
            token = refreshToken
        )

}