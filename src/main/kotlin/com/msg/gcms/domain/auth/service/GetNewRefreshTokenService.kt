package com.msg.gcms.domain.auth.service

import com.msg.gcms.domain.auth.presentation.data.dto.DeviceTokenDto
import com.msg.gcms.domain.auth.presentation.data.response.NewRefreshTokenResponseDto

interface GetNewRefreshTokenService {
    fun execute(refreshToken: String, deviceTokenDto: DeviceTokenDto): NewRefreshTokenResponseDto
}