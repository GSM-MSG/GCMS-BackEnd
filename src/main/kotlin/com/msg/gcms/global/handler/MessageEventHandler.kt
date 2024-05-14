package com.msg.gcms.global.handler

import com.google.firebase.messaging.*
import com.msg.gcms.domain.user.domain.repository.DeviceTokenRepository
import com.msg.gcms.global.event.SendMessageEvent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MessageEventHandler(
    private val deviceTokenRepository: DeviceTokenRepository
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun send(sendMessageEvent: SendMessageEvent) {
        val deviceToken = deviceTokenRepository.findByIdOrNull(sendMessageEvent.user.id)
            ?: return

        if(!deviceToken.token.isNullOrBlank()) {
            val message = Message.builder()
                .setToken(deviceToken.token)
                .putData("type", sendMessageEvent.type.name)
                .setNotification(
                    Notification.builder()
                        .setTitle(sendMessageEvent.title)
                        .setBody(sendMessageEvent.content)
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
}