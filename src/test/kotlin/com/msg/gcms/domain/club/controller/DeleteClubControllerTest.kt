package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.domain.club.utils.ClubConverter
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

class DeleteClubControllerTest : BehaviorSpec({
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
        clubConverter,
        operationPlanConverter
    )

    given("요청이 들어오면") {
        `when`("is received") {
            every { deleteClubService.execute(1) } returns Unit
            val response = clubController.deleteClub(1)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { deleteClubService.execute(1) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})