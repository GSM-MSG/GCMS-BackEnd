package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.dto.UpdateClubDto
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
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

class UpdateClubControllerTest : BehaviorSpec({
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
        val dto = UpdateClubDto(
            name = "test",
            content = "test",
            bannerImg = "https://avatars.githubusercontent.com/u/80161826?v=4",
            contact = "010-0000-0000",
            notionLink = "https://www.notion.so/matsogeum/MSG-afaa5bbf22464d1285ff4209da97e631",
            teacher = "",
            activityImgs = listOf(
                "https://avatars.githubusercontent.com/u/80161826?v=4",
                "https://avatars.githubusercontent.com/u/80161826?v=4",
                "https://avatars.githubusercontent.com/u/80161826?v=4"
            ),
            member = listOf()
        )
        val request = UpdateClubRequest(
            name = "test",
            content = "test",
            bannerImg = "https://avatars.githubusercontent.com/u/80161826?v=4",
            contact = "010-0000-0000",
            notionLink = "https://www.notion.so/matsogeum/MSG-afaa5bbf22464d1285ff4209da97e631",
            teacher = "",
            activityImgs = listOf(
                "https://avatars.githubusercontent.com/u/80161826?v=4",
                "https://avatars.githubusercontent.com/u/80161826?v=4",
                "https://avatars.githubusercontent.com/u/80161826?v=4"
            ),
            member = listOf()
        )
        `when`("is received") {
            every { updateClubService.execute(1, dto) } returns Unit
            val response = clubController.updateClubById(1, request)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { updateClubService.execute(1, dto) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})