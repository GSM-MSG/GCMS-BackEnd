package com.msg.gcms.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gcms.global.logger.filter.RequestLogFilter
import com.msg.gcms.global.security.filter.ExceptionFilter
import com.msg.gcms.global.security.filter.JwtTokenFilter
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtTokenFilter = JwtTokenFilter(jwtTokenProvider)
        val exceptionFilter = ExceptionFilter(objectMapper)
        val requestLogFilter = RequestLogFilter()
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(requestLogFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}