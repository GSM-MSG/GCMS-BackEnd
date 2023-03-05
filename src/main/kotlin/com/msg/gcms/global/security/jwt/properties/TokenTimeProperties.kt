package com.msg.gcms.global.security.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt.time")
class TokenTimeProperties (
     accessTime: Long,
     refreshTime: Long
)