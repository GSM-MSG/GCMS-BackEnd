package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.service.CreateClubService
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class CreateClubControllerTest : BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }
    val findClubListService = mockk<FindClubListService>()
    val createClubService = mockk<CreateClubService>()
    val clubController = ClubController(createClubService, findClubListService, clubConverter())

    given("요청이 들어오면") {
        val dto = ClubDto(
            type = ClubType.FREEDOM,
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
        val request = CreateClubRequest(
            type = ClubType.FREEDOM,
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
            every { createClubService.execute(dto) } returns Unit
            val response = clubController.createClub(request)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { createClubService.execute(dto) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})