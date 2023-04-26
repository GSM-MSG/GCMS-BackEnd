package com.msg.gcms.domain.webhook.config

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordWebhookConfig (
    @Value("\${discord.webhook.url}")
    private val discordWebhookUrl: String
){

    private val httpClient = OkHttpClient()

    @Bean
    fun discordWebhookUrl() = discordWebhookUrl

    @Bean
    fun httpClient() = httpClient
}