package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.impl.OpenClubServiceImpl
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import com.msg.gcms.domain.club.utils.impl.UpdateClubStatusUtil
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.findByIdOrNull

class OpenClubServiceTest : BehaviorSpec({

    @Bean
    fun clubConverter(): ClubConverter = ClubConverterImpl()

    val clubRepository = mockk<ClubRepository>()
    val updateClubStatusUtil = mockk<UpdateClubStatusUtil>()
    val userUtil = mockk<UserUtil>()

    val openClubService = OpenClubServiceImpl(clubRepository, updateClubStatusUtil, clubConverter(), userUtil)

    Given("유저와 동아리가 주어졌을때") {
        val club = TestUtils.data().club().entity(false)
        val clubTrue = TestUtils.data().club().entity(true)
        val headUser = club.user

        When("서비스를 시작하면") {
            every { userUtil.fetchCurrentUser() } returns headUser
            every { clubRepository.findByIdOrNull(club.id) } returns club
            every { clubRepository.save(clubTrue) } returns clubTrue
            every { updateClubStatusUtil.changeIsOpened(club, true) } returns clubTrue
            val result: ClubStatusDto = openClubService.execute(club.id)

            Then("isOpened가 바뀌었는지 확인") {
                result.isOpened shouldBe true
            }
        }
    }
})