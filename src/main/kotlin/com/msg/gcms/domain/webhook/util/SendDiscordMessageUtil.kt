package com.msg.gcms.domain.webhook.util

import com.msg.gcms.domain.club.enums.ClubType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Component

@Component
class SendDiscordMessageUtil (
    val discordWebhookUrl: String,
    val httpClient: OkHttpClient,
    val createMessageUtil: CreateMessageUtil
    ) {
    fun execute(club_name: String, club_type: ClubType, club_img: String){
        val message = createMessageUtil.execute(club_name, club_type, club_img)
        val requestBody = message.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(discordWebhookUrl)
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

}