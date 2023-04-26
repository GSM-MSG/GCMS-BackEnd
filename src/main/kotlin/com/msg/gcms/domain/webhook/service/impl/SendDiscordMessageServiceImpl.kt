package com.msg.gcms.domain.webhook.service.impl

import com.msg.gcms.domain.webhook.service.SendDiscordMessageService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service

@Service
class SendDiscordMessageServiceImpl (val discordWebhookUrl: String, val httpClient: OkHttpClient):
    SendDiscordMessageService {
    override fun execute(message: String){
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