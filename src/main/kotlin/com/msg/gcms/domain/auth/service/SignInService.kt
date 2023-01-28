package com.msg.gcms.domain.auth.service

import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto

interface SignInService {
    fun execute(signInDto: SignInDto): SignInResponseDto
}