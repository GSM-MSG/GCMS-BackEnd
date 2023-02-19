package com.msg.gcms.domain.user.controller

import com.msg.gcms.domain.user.presentaion.UserController
import com.msg.gcms.domain.user.service.*
import com.msg.gcms.domain.user.utils.UserConverter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class WithdrawUserControllerTest : BehaviorSpec({
    val userConverter = mockk<UserConverter>()
    val searchUserService = mockk<SearchUserService>()
    val findUserService = mockk<FindUserService>()
    val updateProfileImgService = mockk<UpdateProfileImgService>()
    val withdrawUserService = mockk<WithdrawUserService>()
    val findUserProfileService = mockk<FindUserProfileService>()
    val clubController = UserController(userConverter, findUserService, searchUserService, updateProfileImgService, withdrawUserService, findUserProfileService)

    given("withdraw user request") {

        `when`("is received") {
            every { withdrawUserService.execute() } returns Unit
            val response = clubController.withdrawUser()

            then("business logic in withdrawUserService should be called") {
                verify(exactly = 1) { withdrawUserService.execute() }
            }
            then("response status should be no_content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})