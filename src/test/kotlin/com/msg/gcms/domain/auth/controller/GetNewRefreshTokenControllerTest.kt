package com.msg.gcms.domain.auth.controller

import com.msg.gcms.domain.auth.presentation.AuthController
import com.msg.gcms.domain.auth.presentation.data.response.NewRefreshTokenResponseDto
import com.msg.gcms.domain.auth.service.GetNewRefreshTokenService
import com.msg.gcms.domain.auth.service.LogoutService
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import com.msg.gcms.domain.auth.util.impl.AuthConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class GetNewRefreshTokenControllerTest : BehaviorSpec({
    @Bean
    fun authConverter(): AuthConverter =
        AuthConverterImpl()

    val getNewRefreshTokenService = mockk<GetNewRefreshTokenService>()
    val signInService = mockk<SignInService>()
    val logoutService = mockk<LogoutService>()
    val authController = AuthController(
        authConverter = authConverter(),
        signInService = signInService,
        getNewRefreshTokenService = getNewRefreshTokenService,
        logoutService = logoutService,
    )

    given("요청이 들어오면") {
        val refreshToken = "thisIsRefresh"
        `when`("is received") {
            every {
                getNewRefreshTokenService.execute(refreshToken)
            } returns NewRefreshTokenResponseDto(
                accessToken = "thisIsAccess",
                refreshToken = "thisIsRefresh",
                accessExp = ZonedDateTime.now(),
                refreshExp = ZonedDateTime.now()
            )

            val response = authController.getNewRefreshToken(refreshToken)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { getNewRefreshTokenService.execute(refreshToken) }
            }
            then("response status should be success") {
                response.statusCode shouldBe HttpStatus.OK
            }

        }

    }

})