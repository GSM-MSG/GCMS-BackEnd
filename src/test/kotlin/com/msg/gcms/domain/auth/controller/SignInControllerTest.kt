package com.msg.gcms.domain.auth.controller

import com.msg.gcms.domain.auth.presentation.AuthController
import com.msg.gcms.domain.auth.presentation.data.dto.SignInDto
import com.msg.gcms.domain.auth.presentation.data.request.SignInRequestDto
import com.msg.gcms.domain.auth.presentation.data.response.SignInResponseDto
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.time.ZonedDateTime

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class SignInControllerTest : BehaviorSpec({
    @Bean
    fun authConverter(): AuthConverter =
        AuthConverterImpl()

    val signInService = mockk<SignInService>()
    val getNewRefreshTokenService = mockk<GetNewRefreshTokenService>()
    val logoutService = mockk<LogoutService>()
    val authController = AuthController(
        authConverter = authConverter(),
        signInService = signInService,
        logoutService = logoutService,
        getNewRefreshTokenService = getNewRefreshTokenService
    )

    given("요청이 들어오면") {
        val dto = SignInDto(
            code = "thisIsCode",
            token = ""
        )
        val request = SignInRequestDto(
            code = "thisIsCode",
            token = ""
        )
        `when`("is received") {
            every { signInService.execute(dto) } returns SignInResponseDto(
                accessToken = "thisIsAccess",
                refreshToken = "thisIsRefresh",
                accessExp = ZonedDateTime.now(),
                refreshExp = ZonedDateTime.now()
            )
            val response = authController.signIn(request)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { signInService.execute(dto) }
            }
            then("response status should be success") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})