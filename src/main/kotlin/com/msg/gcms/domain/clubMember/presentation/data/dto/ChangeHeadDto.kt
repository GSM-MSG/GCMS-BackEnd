package com.msg.gcms.domain.clubMember.presentation.data.dto

import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User

data class ChangeHeadDto(
    val head: User,
    val clubMember: User
)