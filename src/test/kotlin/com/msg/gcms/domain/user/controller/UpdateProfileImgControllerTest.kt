package com.msg.gcms.domain.user.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.presentaion.UserController
import com.msg.gcms.domain.user.presentaion.data.dto.ProfileImgDto
import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.presentaion.data.request.UpdateProfileImgRequestDto
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.service.UpdateProfileImgService
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

class UpdateProfileImgControllerTest : BehaviorSpec({
    @Bean
    fun userConverter(): UserConverter {
        return UserConverterImpl()
    }
    val searchUserService = mockk<SearchUserService>()
    val findUserService = mockk<FindUserService>()
    val updateProfileImgService = mockk<UpdateProfileImgService>()
    val clubController = UserController(userConverter(), findUserService, searchUserService, updateProfileImgService)

    given("update profileImg request") {
        val profileImg = TestUtils.data().user().profileImg()
        val dto = ProfileImgDto(profileImg = profileImg)
        val requestDto = UpdateProfileImgRequestDto(profileImg)

        `when`("is received") {
            every { updateProfileImgService.execute(dto) }
            val response = clubController.updateProfileImg(requestDto)

            then("business logic in updateProfileImgService should be called") {
                verify(exactly = 1) { updateProfileImgService.execute(dto) }
            }
            then("response status should be no_content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})