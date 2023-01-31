package com.msg.gcms.domain.club.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gcms.domain.club.domain.entity.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class CreateClubControllerTest(
    @Autowired
    val mvc: MockMvc,
    @Autowired
    val objectMapper: ObjectMapper,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val tokenProvider: JwtTokenProvider,
) {
    var token: String = ""
    init {
        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, null, listOf(), listOf(), listOf())
        userRepository.save(user)
        token = tokenProvider.generateAccessToken("s21053@gsm.hs.kr")
        val authentication = tokenProvider.authentication(token)
        SecurityContextHolder.getContext().authentication = authentication
    }

    @Test
    fun createClubTest(){
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
        mvc
            .perform(
                MockMvcRequestBuilders
                    .post("/club")
                    .header("Authorization", token)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                MockMvcResultMatchers
                    .status().isCreated
            )
    }
}