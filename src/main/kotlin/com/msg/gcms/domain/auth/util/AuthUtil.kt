package com.msg.gcms.domain.auth.util

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.user.domain.entity.User
import gauth.GAuthUserInfo


interface AuthUtil {

    fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String, role: Role): User
    fun saveRefreshToken(userInfo: User, refreshToken: String, token: String): RefreshToken
}