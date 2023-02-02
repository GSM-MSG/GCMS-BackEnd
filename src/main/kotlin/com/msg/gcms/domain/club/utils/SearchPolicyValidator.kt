package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.user.domain.entity.User

interface SearchPolicyValidator {
    fun validate(user: User): Boolean
}