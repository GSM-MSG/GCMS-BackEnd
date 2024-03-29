package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.domain.club.service.impl.UpdateClubServiceImpl
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.OpeningApplicationConverter
import com.msg.gcms.domain.club.utils.OperationPlanConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import com.msg.gcms.domain.club.utils.impl.OperationPlanConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus


class FindClubListControllerTest : BehaviorSpec({
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

    given("find club list request") {
        val type = ClubType.values().random()
        val clubTypeDto = ClubTypeDto(type)
        val clubListDto = (1..5)
            .map { ClubListDto(it.toLong(), type, "동아리", "동아리 사진","설명") }
        val responseDto = (1..5)
            .map { ClubListResponseDto(it.toLong(), type, "동아리", "동아리 사진", "설명") }

        `when`("is received") {
            every { findClubListService.execute(clubTypeDto) } returns clubListDto
            val response = clubController.findClubListByClubType(type)
            val body = response.body
            then("response body should not be null") {
                response.body shouldNotBe null
            }

            then("business logic in findClubListService should be called") {
                verify(exactly = 1) { findClubListService.execute(clubTypeDto) }
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