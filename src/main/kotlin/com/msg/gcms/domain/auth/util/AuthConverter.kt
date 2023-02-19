package com.msg.gcms.domain.auth.util

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.user.domain.entity.User
import gauth.GAuthUserInfo
import java.util.UUID

interface AuthConverter {

    fun toDto(signInRequestDto: SignInRequestDto): SignInDto

    fun toEntity(gAuthUserInfo: GAuthUserInfo): User

    fun toEntity(userInfo: User, refreshToken: String): RefreshToken

    fun toEntity(userId: UUID?, refreshToken: String): RefreshToken

}