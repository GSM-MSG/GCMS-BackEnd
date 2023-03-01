package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.security.jwt.JwtTokenProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class CloseClubServiceTest : BehaviorSpec({
    val tokenProvider = mockk<JwtTokenProvider>(relaxed = true)
    val userRepository = mockk<UserRepository>()
    val clubRepository = mockk<ClubRepository>()
    val closeClubService =  mockk<CloseClubService>(relaxed = true)
    extension(SpringExtension)
    given("유저와 동아리가 있을때"){
        val role = Role.ROLE_STUDENT
        val user = User(UUID.randomUUID(), "s21053@gsm.hs.kr", "test", 2, 1, 16, mutableListOf(role), null, listOf(), listOf(), listOf())
        every { userRepository.save(user) } returns user
        val director = userRepository.save(user)
        val directorToken = tokenProvider.generateAccessToken(director.email, role)
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
        `when`("서비스를 시작하면"){
            val result = closeClubService.execute(1)
            then("update가 한번 실행되어야 됨"){
                verify(exactly = 1) { clubRepository.save(club) }
            }
            then("isOpened가 바뀌는지 확인"){
                result.isOpened shouldBe false
            }
        }
    }
})