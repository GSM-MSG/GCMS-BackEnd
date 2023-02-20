package com.msg.gcms.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gcms.global.config.FilterConfig
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
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

            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.PATCH, "/auth").permitAll()
            .antMatchers(HttpMethod.DELETE, "/auth").authenticated()

            .antMatchers(HttpMethod.GET, "/club-member/{club_id}").authenticated()
            .antMatchers(HttpMethod.PATCH, "/club-member/{club_id}").authenticated()

            .antMatchers(HttpMethod.POST, "/club").authenticated()
            .antMatchers(HttpMethod.GET, "/club").permitAll()
            .antMatchers(HttpMethod.GET, "/club/{club_id}").authenticated()
            .antMatchers(HttpMethod.PATCH, "/club/{club_id}").authenticated()
            .antMatchers(HttpMethod.PATCH, "/club/{club_id}/close").authenticated()
            .antMatchers(HttpMethod.PATCH, "/club/{club_id}/open").permitAll()
            .antMatchers(HttpMethod.DELETE, "/club/{club_id}/exit").authenticated()
            .antMatchers(HttpMethod.DELETE, "/club/{club_id}").authenticated()

            .antMatchers(HttpMethod.GET, "/user").authenticated()
            .antMatchers(HttpMethod.GET, "/user/search").authenticated()
            .antMatchers(HttpMethod.PATCH, "/user").authenticated()
            .antMatchers(HttpMethod.DELETE, "/user").authenticated()

            .antMatchers(HttpMethod.GET, "/applicant/{club_id}").authenticated()
            .antMatchers(HttpMethod.POST, "/applicant/{club_id}").authenticated()
            .antMatchers(HttpMethod.DELETE, "/applicant/{club_id}").authenticated()
            .antMatchers(HttpMethod.POST, "/applicant/{club_id}/accept").authenticated()
            .antMatchers(HttpMethod.POST, "/applicant/{club_id}/reject").authenticated()

            .antMatchers(HttpMethod.POST, "/image").authenticated()
            .anyRequest().denyAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))

            .and()
            .apply(FilterConfig(jwtTokenProvider, objectMapper))
            .and()
            .build()
    }

}