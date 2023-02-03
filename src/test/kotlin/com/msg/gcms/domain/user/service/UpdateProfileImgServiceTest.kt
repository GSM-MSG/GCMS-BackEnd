package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.ProfileImgDto
import com.msg.gcms.domain.user.service.impl.UpdateProfileImgServiceImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UpdateProfileImgServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val userUtil = mockk<UserUtil>()
    val updateProfileImgServiceImpl = UpdateProfileImgServiceImpl(userUtil, userRepository)

    given("sex") {
        val user = TestUtils.data().user().entity()
        val profileImg = TestUtils.data().user().profileImg()
        val dto = ProfileImgDto(profileImg)

        `when`("is invoked") {
            every { userUtil.fetchCurrentUser() } returns user
            every { userRepository.save(user) } returns user
            updateProfileImgServiceImpl.execute(dto)

            then("profileImg in user should be profileImg") {
                user.profileImg shouldBe profileImg
            }
        }
    }
})