package com.msg.gcms.domain.webhook.service

interface SendDiscordMessageService {
    fun execute(message: String)
}