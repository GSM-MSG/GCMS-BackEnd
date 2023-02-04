package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class OpenClubControllerTest : BehaviorSpec({

    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }

    val findClubListService = mockk<FindClubListService>()
    val createClubService = mockk<CreateClubService>()
    val updateClubService = mockk<UpdateClubService>()
    val closeClubService = mockk<CloseClubService>()
    val openClubService = mockk<OpenClubService>()
    val exitClubService = mockk<ExitClubService>()

    val clubController = ClubController(
        createClubService,
        findClubListService,
        updateClubService,
        closeClubService,
        openClubService,
        exitClubService,
        clubConverter()
    )
    Given("요청이 들어오면") {
        When("is received") {
            every { openClubService.execute(1) } returns ClubStatusDto(true)
            val response = clubController.openClub(1)

            Then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { openClubService.execute(1) }
            }
            Then("응답 상태코드가 NO_CONTENT여야 한다.") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})