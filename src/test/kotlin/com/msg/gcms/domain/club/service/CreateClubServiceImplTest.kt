package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class CreateClubServiceImplTest : BehaviorSpec({
    val clubRepository = mockk<ClubRepository>()
    val tokenProvider = mockk<JwtTokenProvider>(relaxed = true)
    val userRepository = mockk<UserRepository>()
    val createClubService = mockk<CreateClubService>(relaxed = true)
    given("유저와 clubDto가 주어질때") {
        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, null, listOf(), listOf(), listOf())
        val role = Role.STUDENT
        every { userRepository.save(user) } returns user
        userRepository.save(user)
        val token = tokenProvider.generateAccessToken("s21053@gsm.hs.kr", role)
        val authentication = tokenProvider.authentication(token)
        SecurityContextHolder.getContext().authentication = authentication
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
        `when`("동아리를 생성하면") {
            createClubService.execute(clubRequest)
            every { clubRepository.findByIdOrNull(1) } returns Club(
                type = ClubType.FREEDOM,
                name = "test",
                content = "test",
                bannerImg = "https://avatars.githubusercontent.com/u/80161826?v=4",
                contact = "010-0000-0000",
                notionLink = "https://www.notion.so/matsogeum/MSG-afaa5bbf22464d1285ff4209da97e631",
                teacher = "",
                activityImg = listOf(),
                user = user,
                applicant = listOf(),
                clubMember = listOf(),
                isOpened = true
            )
            val club = clubRepository.findByIdOrNull(1)
            then("id가 1인 동아리가 존재해야하고 해당 동아리의 내용은 clubRequest와 같아야한다") {
                assertSoftly(club!!) {
                    name shouldBe clubRequest.name
                    content shouldBe clubRequest.content
                    type shouldBe clubRequest.type
                    bannerImg shouldBe clubRequest.bannerImg
                    contact shouldBe clubRequest.contact
                    notionLink shouldBe clubRequest.notionLink
                    teacher shouldBe clubRequest.teacher
                }
            }
        }

    }
})