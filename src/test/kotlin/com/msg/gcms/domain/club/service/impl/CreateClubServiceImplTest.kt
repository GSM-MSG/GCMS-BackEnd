package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
class CreateClubServiceImplTest(
    @Autowired
    val createClubService: CreateClubServiceImpl,
    @Autowired
    val clubRepository: ClubRepository,
    @Autowired
    val tokenProvider: JwtTokenProvider,
    @Autowired
    val userRepository: UserRepository,
) {
    @BeforeEach
    fun generateToken(){
        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, null, listOf(), listOf(), listOf())
        userRepository.save(user)
        val token = tokenProvider.generateAccessToken("s21053@gsm.hs.kr")
        val authentication = tokenProvider.authentication(token)
        SecurityContextHolder.getContext().authentication = authentication
    }

    @Test
    fun createClubTest() {
        //given
        val clubRequest = ClubDto(
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

        //when
        createClubService.execute(clubRequest)
        println("clubRepository.existsById(1) = ${clubRepository.existsById(1)}")

        //then
        Assertions.assertThat(clubRepository.existsById(1)).isTrue()
        val club = clubRepository.findById(1).orElseThrow { throw RuntimeException() }
        Assertions.assertThat(club.name).isEqualTo(clubRequest.name)
        Assertions.assertThat(club.content).isEqualTo(clubRequest.content)
        Assertions.assertThat(club.type).isEqualTo(clubRequest.type)
        Assertions.assertThat(club.bannerImg).isEqualTo(clubRequest.bannerImg)
        Assertions.assertThat(club.contact).isEqualTo(clubRequest.contact)
        Assertions.assertThat(club.notionLink).isEqualTo(clubRequest.notionLink)
        Assertions.assertThat(club.teacher).isEqualTo(clubRequest.teacher)
    }
}