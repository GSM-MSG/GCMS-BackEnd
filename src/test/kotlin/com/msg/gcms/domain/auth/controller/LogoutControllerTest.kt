package com.msg.gcms.domain.auth.controller

import com.msg.gcms.domain.auth.presentation.AuthController
import com.msg.gcms.domain.auth.service.GetNewRefreshTokenService
import com.msg.gcms.domain.auth.service.LogoutService
import com.msg.gcms.domain.auth.service.SignInService
import com.msg.gcms.domain.auth.util.AuthConverter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class LogoutControllerTest : BehaviorSpec({
    val authConverter = mockk<AuthConverter>()
    val signInService = mockk<SignInService>()
    val getNewRefreshTokenService = mockk<GetNewRefreshTokenService>()
    val logoutService = mockk<LogoutService>()
    val authController = AuthController(
        authConverter = authConverter,
        signInService = signInService,
        logoutService = logoutService,
        getNewRefreshTokenService = getNewRefreshTokenService
    )

    given("요청이 들어오면") {
        `when`("is received") {
            every { logoutService.execute() } returns Unit
            val response = authController.logout()
            then("사비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { logoutService.execute() }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }

})