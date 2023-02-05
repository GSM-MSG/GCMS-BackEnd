package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.service.impl.DeleteClubServiceImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull

class DeleteClubServiceTest : BehaviorSpec({
    val clubRepository = mockk<ClubRepository>()
    val userUtil = mockk<UserUtil>()
    val deleteClubService = DeleteClubServiceImpl(clubRepository, userUtil)
    given("유저와 동아리가 주어지면"){
        val user = TestUtils.data().user().entity()
        val otherUser = TestUtils.data().user().entity()
        every { userUtil.fetchCurrentUser() } returns user
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
            clubMember = listOf(),
            teacher = ""
        )
        every { clubRepository.findByIdOrNull(1) } returns club
        `when`("deleteClubService를 실행하고"){
            every { clubRepository.delete(club) } returns Unit
            deleteClubService.execute(1)
            then("그때 delete쿼리가 실행되어야함"){
                verify(exactly = 1){ clubRepository.delete(club) }
            }
        }
        `when`("부장이 아닌 유저가 지울려할때"){
            every { userUtil.fetchCurrentUser() } returns otherUser
            then("HeadNotSameException이 터져야함"){
                shouldThrow<HeadNotSameException> {
                    deleteClubService.execute(1)
                }
            }
        }
    }
})