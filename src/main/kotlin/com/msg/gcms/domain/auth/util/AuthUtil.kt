package com.msg.gcms.domain.auth.util

import com.msg.gcms.domain.admin.domain.entity.Admin
import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.user.domain.entity.User
import gauth.GAuthUserInfo


interface AuthUtil {

    fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String)

    fun saveNewAdmin(gAuthUserInfo: GAuthUserInfo, refreshToken: String)

    fun saveNewRefreshToken(userInfo: User, refreshToken: String): RefreshToken
    fun saveNewRefreshToken(adminInfo: Admin, refreshToken: String): RefreshToken
}