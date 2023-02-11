package com.msg.gcms.domain.auth.presentation

import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestController("/auth")
class AuthController(
    private val authConverter: AuthConverter,
    private val signInService: SignInService
) {

    @PostMapping
    fun signIn(@RequestBody signInRequestDto: SignInRequestDto): ResponseEntity<SignInResponseDto> =
        authConverter.toDto(signInRequestDto)
            .let { ResponseEntity.ok(signInService.execute(it)) }

}