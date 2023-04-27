package com.msg.gcms.domain.webhook.util.impl

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.webhook.config.DiscordWebhookConfig
import com.msg.gcms.domain.webhook.util.DiscordUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DiscordUtilImpl(val httpClient: OkHttpClient): DiscordUtil{

    @Value("\${discord.webhook.url}")
    lateinit var discordWebhookProperties: String

    override fun sendDiscordMessage(message: String) {
        val requestBody = message.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(discordWebhookProperties)
            .post(requestBody)
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                println("띵동이가 동아리 신설 요청을 성공적으로 전달했어요.")
            } else {
                println("띵동이가 동아리 신설 요청 대신 ${response.code}코드를 보냈어요.")
            }
        }
    }

    override fun applyMessage(clubName: String, clubType: ClubType, clubBannerImg: String) = """
            "content": "동아리 신설 요청이 들어왔어요.",
            "embeds": [
            {
                "title": "새로운 동아리가 승인을 기다리고 있어요!",
                "color": 5725911,
                "fields": [
                {
                    "name": "동아리 이름",
                    "value": "$clubName",
                    "inline": true
                },
                {
                    "name": "동아리 유형",
                    "value": "${clubType.name}",
                    "inline": true
                }
                ],
                "image": {
                "url": "$clubBannerImg"
            }
            }
            ],
            "attachments": []
        }
        """.trimIndent()
}