package com.msg.gcms.domain.auth.presentation

import com.msg.gcms.domain.auth.presentation.data.request.DeviceTokenRequest
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.presentation.data.response.NewRefreshTokenResponseDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
import com.msg.gcms.domain.auth.service.GetNewRefreshTokenService
import com.msg.gcms.domain.auth.service.LogoutService
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestController("/auth")
class AuthController(
    private val signInService: SignInService,
    private val getNewRefreshTokenService: GetNewRefreshTokenService,
    private val logoutService: LogoutService,
    private val authConverter: AuthConverter
) {

    @PostMapping
    fun signIn(@Valid @RequestBody signInRequestDto: SignInRequestDto): ResponseEntity<SignInResponseDto> =
        authConverter.toDto(signInRequestDto)
            .let { ResponseEntity.ok(signInService.execute(it)) }

    @PatchMapping
    fun getNewRefreshToken(@RequestHeader("Refresh-Token") refreshToken: String, @RequestBody(required = false) deviceTokenRequest: DeviceTokenRequest?): ResponseEntity<NewRefreshTokenResponseDto> =
        ResponseEntity.ok().body(getNewRefreshTokenService.execute(refreshToken = refreshToken, authConverter.toDto(deviceTokenRequest?:DeviceTokenRequest(null))))

    @DeleteMapping
    fun logout(): ResponseEntity<Void> {
        logoutService.execute()
        return ResponseEntity.noContent().build()
    }

}