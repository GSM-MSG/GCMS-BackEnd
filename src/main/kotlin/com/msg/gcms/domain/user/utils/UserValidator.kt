package com.msg.gcms.domain.user.utils

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User

interface UserValidator {
   fun validateUser(userList: List<User>, clubType: ClubType): List<User>
}