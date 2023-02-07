package com.msg.gcms.domain.user.controller

import com.msg.gcms.domain.user.presentaion.UserController
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.service.UpdateProfileImgService
import com.msg.gcms.domain.user.service.WithdrawUserService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.domain.user.utils.impl.UserConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class FindUserControllerTest : BehaviorSpec({
    @Bean
    fun userConverter(): UserConverter {
        return UserConverterImpl()
    }
    val searchUserService = mockk<SearchUserService>()
    val findUserService = mockk<FindUserService>()
    val updateProfileImgService = mockk<UpdateProfileImgService>()
    val withdrawUserService = mockk<WithdrawUserService>()
    val clubController = UserController(userConverter(), findUserService, searchUserService, updateProfileImgService, withdrawUserService)

    given("find user request") {
        val userDto = TestUtils.data().user().userDto()
        val responseDto = TestUtils.data().user().userResponseDto(userDto)

        `when`("is received") {
            every { findUserService.execute() } returns userDto
            val response = clubController.findUser()
            val body = response.body

            then("response body should not be null") {
                response.body shouldNotBe null
            }

            then("business logic in findUserService should be called") {
                verify(exactly = 1) { findUserService.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
            then("result should be same as responseDto") {
                body shouldBe responseDto
            }
        }
    }
})