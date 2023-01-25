package com.msg.gcms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class GcmsApplication

fun main(args: Array<String>) {
	runApplication<GcmsApplication>(*args)
}
