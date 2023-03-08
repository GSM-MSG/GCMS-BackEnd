package com.msg.gcms.global.util

import com.msg.gcms.global.fcm.enums.SendType
import java.util.*

interface MessageSendUtil {
    fun sendMessage(token: String, title: String, content: String, type: SendType, identify: UUID?)
}