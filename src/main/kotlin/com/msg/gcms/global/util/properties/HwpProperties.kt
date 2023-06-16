package com.msg.gcms.global.util.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "hwp.file")
class HwpProperties(
    val url: String
)