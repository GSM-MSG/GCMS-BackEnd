package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class UpdateClubServiceImplTest: BehaviorSpec({
    val updateClubService =  mockk<UpdateClubService>(relaxed = true)
    val tokenProvider = mockk<JwtTokenProvider>(relaxed = true)
    val userRepository = mockk<UserRepository>()
    val clubRepository = mockk<ClubRepository>()
    extension(SpringExtension)
    given("유저, 동아리, clubDto가 주어질때"){
        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, null, listOf(), listOf(), listOf())
        every { userRepository.save(user) } returns user
        val director = userRepository.save(user)
        val directorToken = tokenProvider.generateAccessToken(director.email)
        val authentication = tokenProvider.authentication(directorToken)
        SecurityContextHolder.getContext().authentication = authentication
        val club = Club(
            id = 1,
            name = "testClub",
            content = "test",
            bannerImg = "testImg",
            notionLink = "",
            contact = "010-0000-0000",
            type = ClubType.FREEDOM,
            isOpened = true,
            user = director,
            activityImg = listOf(),
            applicant = listOf(),
            clubMember = listOf(),
            teacher = ""
        )
        every { clubRepository.save(club) } returns club
        clubRepository.save(club)
        `when`("부장이 동아리를 수정할때"){
            val updateRequest = ClubDto(
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
            updateClubService.execute(club.id, updateRequest)
            then("업데이트된 동아리는 updateRequest의 내용과 같아야한다"){
                every { clubRepository.findById(club.id) } returns Optional.of(club)
                val club = clubRepository.findById(club.id).orElseThrow { throw RuntimeException() }
                assertSoftly(club) {
                    name shouldBe updateRequest.name
                    content shouldBe updateRequest.content
                    type shouldBe updateRequest.type
                    bannerImg shouldBe updateRequest.bannerImg
                    contact shouldBe updateRequest.contact
                    notionLink shouldBe updateRequest.notionLink
                    teacher shouldBe updateRequest.teacher
                }
            }
        }
    }
})
