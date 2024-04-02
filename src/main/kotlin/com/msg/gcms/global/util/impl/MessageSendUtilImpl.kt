package com.msg.gcms.global.util.impl

import com.google.firebase.messaging.*
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.DeviceTokenRepository
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MessageSendUtilImpl(
    private val deviceTokenRepository: DeviceTokenRepository,
) : MessageSendUtil {
    override fun sendMessage(token: String, title: String, content: String, type: SendType) {
        val message = Message.builder()
            .setToken(token)
            .putData("type", type.name)
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

    override fun send(user: User, title: String, content: String, type: SendType) {
        val deviceToken = deviceTokenRepository.findByIdOrNull(user.id)
            ?: return
        if(deviceToken.token.isBlank())
            return
        sendMessage(deviceToken.token, title, content, type)
    }
}