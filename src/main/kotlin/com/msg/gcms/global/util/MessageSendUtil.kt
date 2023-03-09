package com.msg.gcms.global.util

import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.fcm.enums.SendType
import java.util.*

interface MessageSendUtil {
    fun sendMessage(token: String, title: String, content: String, type: SendType)
    fun send(user: User, title: String, content: String, type: SendType)
}