package com.msg.gcms.global.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("https://gcms-front-end-v2.vercel.app", "http://localhost:3000", "https://gcms.msg-team.com")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}
