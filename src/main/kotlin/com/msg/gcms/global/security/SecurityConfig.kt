package com.msg.gcms.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gcms.global.security.filter.ExceptionFilter
import com.msg.gcms.global.security.handler.CustomAccessDeniedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val exceptionFilter: ExceptionFilter,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors().and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(RequestMatcher { request ->
                CorsUtils.isPreFlightRequest(request)
            }).permitAll()

            // Auth
            .antMatchers("/auth/**").permitAll()

            // Club
            .antMatchers("/club/**").authenticated()

            // Club Member
            .antMatchers("/club-member/**").authenticated()

            // Applicant
            .antMatchers("/applicant/**").authenticated()

            //User
            .antMatchers("/user/**").authenticated()

            // Image
            .antMatchers("/image/**").authenticated()

            .anyRequest().denyAll()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(CustomAccessDeniedHandler(objectMapper))

            .and()
            .addFilterBefore(exceptionFilter, UsernamePasswordAuthenticationFilter::class.java)

            .build()
    }
}