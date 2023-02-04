package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.service.impl.ExitClubServiceImpl
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull

class ExitClubServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>(relaxed = true)
    val clubRepository = mockk<ClubRepository>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val exitClubServiceImpl = ExitClubServiceImpl(clubRepository, clubMemberRepository, userUtil)
    extension(SpringExtension)
    given("유저, 동아리, 동아리 부장, 동아리 구성원이 주어질때"){
        val user = TestUtils.data().user().entity()
        val applicant = TestUtils.data().user().entity()
        val otherUser = TestUtils.data().user().entity()
        every { userUtil.fetchCurrentUser() } returns applicant
        val tempClub = Club(
            id = 1,
            name = "testClub",
            content = "test",
            bannerImg = "testImg",
            notionLink = "",
            contact = "010-0000-0000",
            type = ClubType.FREEDOM,
            isOpened = true,
            user = user,
            activityImg = listOf(),
            applicant = listOf(),
            clubMember = listOf(),
            teacher = ""
        )
        val clubMember = ClubMember(1, tempClub, applicant)
        val club = Club(
            id = 1,
            name = "testClub",
            content = "test",
            bannerImg = "testImg",
            notionLink = "",
            contact = "010-0000-0000",
            type = ClubType.FREEDOM,
            isOpened = true,
            user = user,
            activityImg = listOf(),
            applicant = listOf(),
            clubMember = listOf(clubMember),
            teacher = ""
        )
        every { clubRepository.save(club) } returns club
        clubRepository.save(club)
        every { clubRepository.findByIdOrNull(club.id) } returns club
        every { clubMemberRepository.delete(clubMember) } returns Unit
        `when`("서비스를 실행하면"){
            exitClubServiceImpl.execute(club.id)
            then("신청자가 존재하지 않아야한다."){
                verify(exactly = 1) { clubMemberRepository.delete(clubMember) }
            }
        }
    }
})