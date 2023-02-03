package com.msg.gcms.domain.user.domain.repository

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User

interface UserRepositoryCustom {
    fun findUserNotJoin(type: ClubType, name: String): List<User>
}