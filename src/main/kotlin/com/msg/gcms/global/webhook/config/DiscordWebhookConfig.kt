package com.msg.gcms.global.webhook.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordWebhookConfig {

    private val httpClient = OkHttpClient()

    @Bean
    fun httpClient() = httpClient
}