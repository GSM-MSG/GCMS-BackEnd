package com.msg.gcms.global.security.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt.time")
class TokenTimeProperties (
    val accessTime: Long,
    val refreshTime: Long
)