package com.msg.gcms.domain.auth.util

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import com.msg.gcms.domain.user.domain.entity.User
import gauth.GAuthUserInfo


interface AuthUtil {

    fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String?)

    fun saveNewAdmin(gAuthUserInfo: GAuthUserInfo, refreshToken: String, token: String?)

    fun saveNewRefreshToken(userInfo: User, refreshToken: String, token: String?): RefreshToken
}