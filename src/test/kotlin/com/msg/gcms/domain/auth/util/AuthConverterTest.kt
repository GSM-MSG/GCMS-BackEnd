package com.msg.gcms.domain.auth.util

import com.msg.gcms.domain.auth.util.impl.AuthConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.context.annotation.Bean
import java.time.ZonedDateTime

class AuthConverterTest : BehaviorSpec({
    @Bean
    fun authConverter() : AuthConverter =
        AuthConverterImpl()

    given("convert toSignInDto request") {
        val code = "gAuthLoginCode"
        val signInRequestDto = TestUtils.data().auth().signInRequestDto(code = code)
        val signInDto = TestUtils.data().auth().signInDto(code = code)
        `when`("is converting code") {
            val convertedDto = authConverter().toDto(signInRequestDto)
            then("convertedDto should be as same signInDto") {
                convertedDto.code shouldBe signInDto.code
            }
        }
    }

    given("convert toSignInResponseDto request") {
        val accessToken = "thisAccessToken"
        val refreshToken = "thisRefreshToken"
        val accessExp = ZonedDateTime.now()
        val refreshExp = ZonedDateTime.now()
        val signInResponseDto = TestUtils.data().auth().signInResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
        `when`("is converting signInResponseDto") {
            val convertedDto = authConverter().toResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
                accessExp = accessExp,
                refreshExp = refreshExp
            )
            then("convertedDto should be as same responseDto") {
                convertedDto shouldBe signInResponseDto
            }
        }
    }
})