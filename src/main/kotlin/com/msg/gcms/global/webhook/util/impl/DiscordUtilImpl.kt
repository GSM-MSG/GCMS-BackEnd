package com.msg.gcms.global.webhook.util.impl

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.enums.ClubType.*
import com.msg.gcms.global.webhook.util.DiscordUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory

@Component
class DiscordUtilImpl(
    private val httpClient: OkHttpClient
) : DiscordUtil {

    @Value("\${discord.webhook.url}")
    private lateinit var discordWebhookUrl: String

    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    override fun sendDiscordMessage(message: String) {
        val requestBody = message.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(discordWebhookUrl)
            .post(requestBody)
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                log.trace("띵동이가 동아리 신설 요청을 성공적으로 전달했어요.")
            } else {
                log.error("띵동이가 동아리 신설 요청 대신 ${response.code}코드를 보냈어요.")
            }
        }
    }

    override fun createClubMessage(clubName: String, clubType: ClubType, clubBannerImg: String) = """
        {
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
                            "value": "${
                                when(clubType){
                                    MAJOR -> "전공동아리"
                                    FREEDOM -> "자율동아리"
                                    EDITORIAL -> "사설동아리"
                                }
                            }",
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