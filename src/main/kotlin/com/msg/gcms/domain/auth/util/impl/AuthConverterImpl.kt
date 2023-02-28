package com.msg.gcms.domain.auth.util.impl

import com.msg.gcms.domain.admin.domain.entity.Admin
import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.user.domain.entity.User
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthConverterImpl : AuthConverter {
    override fun toDto(signInRequestDto: SignInRequestDto): SignInDto =
        SignInDto(
            code = signInRequestDto.code
        )


    override fun toEntity(gAuthUserInfo: GAuthUserInfo): User =
        User(
            id = UUID.randomUUID(),
            email = gAuthUserInfo.email,
            nickname = gAuthUserInfo.name,
            grade = gAuthUserInfo.grade,
            classNum = gAuthUserInfo.classNum,
            number = gAuthUserInfo.num,
            profileImg = gAuthUserInfo.profileUrl,
        )

    override fun toAdminEntity(gAuthUserInfo: GAuthUserInfo): Admin =
        Admin(
            id = UUID.randomUUID(),
            email = gAuthUserInfo.email
        )

    override fun toEntity(userInfo: User, refreshToken: String): RefreshToken =
        RefreshToken(
            userId = userInfo.id,
            token = refreshToken
        )

    override fun toEntity(adminInfo: Admin, refreshToken: String): RefreshToken =
        RefreshToken(
            userId = adminInfo.id,
            token = refreshToken
        )

    override fun toEntity(userId: UUID?, refreshToken: String): RefreshToken =
        RefreshToken(
            userId = userId,
            token = refreshToken
        )
}