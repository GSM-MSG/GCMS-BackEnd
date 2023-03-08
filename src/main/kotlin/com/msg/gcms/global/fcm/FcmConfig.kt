package com.msg.gcms.global.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.annotation.PostConstruct


@Configuration
class FcmConfig(
    @Value("\${fcm.value}")
    private val fcmValue: String
) {

    @PostConstruct
    private fun initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                val options: FirebaseOptions = FirebaseOptions.builder()
                    .setCredentials(
                        GoogleCredentials.fromStream(
                            FileInputStream(fcmValue)
                        )
                    )
                    .build()
                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}