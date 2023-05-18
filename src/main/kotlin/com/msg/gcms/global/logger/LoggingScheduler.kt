package com.msg.gcms.global.logger

import com.msg.gcms.global.webhook.util.impl.DiscordUtilImpl
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

@Component
class LoggingScheduler(
    private val discordUtilImpl: DiscordUtilImpl
) {
    @Scheduled(cron = "59 59 23 * * ?", zone = "Asia/Seoul")
    fun sendLog(){
        val requestDir = "./logs/access-${LocalDate.now()}.0.log"
        val requestFileLog = Files.readAllLines(Paths.get(ClassPathResource(requestDir).uri)).reduce { acc, str -> "$acc\n$str" }
        val errorDir = "./logs/error-${LocalDate.now()}.0.log"
        val errorFileLog = Files.readAllLines(Paths.get(ClassPathResource(errorDir).uri)).reduce { acc, str -> "$acc\n$str" }
        Files.deleteIfExists(Paths.get(ClassPathResource(requestDir).uri))
        Files.deleteIfExists(Paths.get(ClassPathResource(errorDir).uri))
        println("==request==")
        println(requestFileLog)
        println("==error==")
        println(errorFileLog)
        discordUtilImpl.sendDiscordMessage("**gcms 요청 로그**\n$requestFileLog")
        discordUtilImpl.sendDiscordMessage("**gcms 에러 로그**\n$errorFileLog")
    }
}