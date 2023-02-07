package com.msg.gcms.domain.user.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.presentaion.UserController
import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
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

class SearchUserControllerTest : BehaviorSpec({
    @Bean
    fun userConverter(): UserConverter {
        return UserConverterImpl()
    }
    val searchUserService = mockk<SearchUserService>()
    val findUserService = mockk<FindUserService>()
    val updateProfileImgService = mockk<UpdateProfileImgService>()
    val withdrawUserService = mockk<WithdrawUserService>()
    val clubController = UserController(userConverter(), findUserService, searchUserService, updateProfileImgService, withdrawUserService)

    given("search user request") {
        val type = ClubType.values().random()
        val name = TestUtils.data().user().entity().nickname
        val dto = SearchRequirementDto(type, name)
        val size = (1..100).random()
        val searchUserDto = (1..size)
            .map{ TestUtils.data().user().searchUserDto() }
        val responseDto = searchUserDto
            .map { TestUtils.data().user().searchUserResponseDto(it) }

        `when`("is received") {
            every { searchUserService.execute(dto) } returns searchUserDto
            val response = clubController.searchUser(type, name)
            val body = response.body

            then("response body should not be null") {
                response.body shouldNotBe null
            }

            then("business logic in searchUserService should be called") {
                verify(exactly = 1) { searchUserService.execute(dto) }
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