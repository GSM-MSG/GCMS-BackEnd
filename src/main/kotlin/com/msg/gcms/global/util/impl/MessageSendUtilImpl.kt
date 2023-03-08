package com.msg.gcms.global.util.impl

import com.google.firebase.messaging.*
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
import org.springframework.stereotype.Component
import java.util.*

@Component
class MessageSendUtilImpl : MessageSendUtil {
    override fun sendMessage(token: String, title: String, content: String, type: SendType, identify: UUID?) {
        val message = Message.builder()
            .setToken(token)
            .putData("type", type.name)
            .putData("identify", identify.toString())
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(content)
                    .build()
            )
            .setApnsConfig(
                ApnsConfig.builder()
                    .setAps(Aps.builder().setSound("default").build())
                    .build()
            )
            .build()

        FirebaseMessaging.getInstance().sendAsync(message)
    }
}