package com.msg.gcms.global.logger

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.msg.gcms.global.webhook.util.DiscordUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File

@Component
class LoggingScheduler(
    private val amazonS3: AmazonS3,
    private val discordUtil: DiscordUtil
) {
    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucket: String

    @Value("\${cloud.aws.s3.url}")
    lateinit var url: String

    @Scheduled(cron = "59 59 23 * * ?", zone = "Asia/Seoul")
    fun sendLog(){
        val logDir = "./src/main/resources/logs/"
        val logDirectory = File(logDir)
        val logUrlList = mutableListOf<String>()
        logDirectory.listFiles()
            .forEach {file ->
                val fileName = file.name
                val objectMetadata = ObjectMetadata()
                objectMetadata.contentLength = file.length()
                objectMetadata.contentType = "text/plain"
                amazonS3.putObject(
                    PutObjectRequest(bucket, fileName, file.inputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
                file.delete()
                logUrlList.add(url+fileName)
            }
        val message = StringBuilder("**로그 목록**\\n")
        logUrlList.forEachIndexed { idx, str ->
            message.append("${idx + 1} $str\\n")
        }
        discordUtil.toSingleDiscordMessage(message.toString())
            .let { discordUtil.sendDiscordMessage(it) }
    }

    fun String.toJson(): String = """
        {
            "content":"$this"
        }
    """.trimIndent()
}