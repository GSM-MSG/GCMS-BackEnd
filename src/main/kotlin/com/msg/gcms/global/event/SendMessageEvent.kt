package com.msg.gcms.global.event

import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.fcm.enums.SendType

data class SendMessageEvent(
    val user: User,
    val title: String,
    val content: String,
    val type: SendType
)