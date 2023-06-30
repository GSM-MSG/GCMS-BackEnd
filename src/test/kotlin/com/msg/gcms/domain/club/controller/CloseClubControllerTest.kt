package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.OpeningApplicationConverter
import com.msg.gcms.domain.club.utils.OperationPlanConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import com.msg.gcms.domain.club.utils.impl.OperationPlanConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class CloseClubControllerTest : BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }

    @Bean
    fun operationPlanConverter(): OperationPlanConverter {
        return OperationPlanConverterImpl()
    }

    val findClubListService = mockk<FindClubListService>()
    val createClubService = mockk<CreateClubService>()
    val updateClubService = mockk<UpdateClubService>()
    val openClubService = mockk<OpenClubService>()
    val clubConverter = clubConverter()
    val closeClubService = mockk<CloseClubService>()
    val exitClubService = mockk<ExitClubService>()
    val deleteClubService = mockk<DeleteClubService>()
    val detailClubService = mockk<DetailClubService>()
    val operationPlanConverter = operationPlanConverter()
    val createOperationPlanService = mockk<CreateOperationPlanService>()
    val createOpeningApplicationService = mockk<CreateOpeningApplicationService>()
    val openingApplicationConverter = mockk<OpeningApplicationConverter>()
    val clubController = ClubController(
        createClubService,
        findClubListService,
        updateClubService,
        closeClubService,
        openClubService,
        exitClubService,
        deleteClubService,
        detailClubService,
        createOperationPlanService,
        createOpeningApplicationService,
        clubConverter,
        operationPlanConverter,
        openingApplicationConverter
    )

    given("요청이 들어오면") {
        `when`("is received") {
            every { closeClubService.execute(1) } returns ClubStatusDto(false)
            val response = clubController.closeClub(1)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { closeClubService.execute(1) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})