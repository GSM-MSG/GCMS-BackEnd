package com.msg.gcms.domain.clubmember.service

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto
import com.msg.gcms.domain.clubMember.service.impl.DelegateHeadServiceImpl
import com.msg.gcms.domain.clubMember.util.UpdateClubHeadUtil
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.global.event.SendMessageEvent
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull

class DelegateHeadServiceTest : BehaviorSpec({
    val clubRepository = mockk<ClubRepository>()
    val userUtil = mockk<UserUtil>()
    val userRepository = mockk<UserRepository>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val updateClubHeadUtil = mockk<UpdateClubHeadUtil>()
    val applicationEventPublisher = mockk<ApplicationEventPublisher>()

    val delegateHeadServiceImpl = DelegateHeadServiceImpl(clubRepository, clubMemberRepository, userRepository, updateClubHeadUtil, userUtil, applicationEventPublisher)

    given("동아리, 부장, 동아리 멤버가 주어지고"){
        val head = TestUtils.data().user().entity()
        val member = TestUtils.data().user().entity()
        val tempClub = Club(
            id = 1,
            name = "testClub",
            content = "test",
            bannerImg = "testImg",
            notionLink = "",
            contact = "010-0000-0000",
            type = ClubType.FREEDOM,
            isOpened = true,
            user = head,
            activityImg = listOf(),
            applicant = listOf(),
            clubMember = listOf(),
            teacher = ""
        )
        val clubMember = ClubMember(1, tempClub, member)
        val club = Club(
            id = 1,
            name = "testClub",
            content = "test",
            bannerImg = "testImg",
            notionLink = "",
            contact = "010-0000-0000",
            type = ClubType.FREEDOM,
            isOpened = true,
            user = head,
            activityImg = listOf(),
            applicant = listOf(),
            clubMember = listOf(clubMember),
            teacher = ""
        )
        every { userUtil.fetchCurrentUser() } returns head
        every { clubRepository.findByIdOrNull(1) } returns club
        every {clubRepository.save(club) } returns club
        every { clubMemberRepository.delete(clubMember) } returns Unit
        every { clubMemberRepository.save(clubMember) } returns clubMember
        every { userRepository.existsById(member.id) } returns true
        every { clubMemberRepository.findAllByClub(club) } returns listOf(clubMember)
        every { updateClubHeadUtil.updateClubHead(club, clubMember, head) } returns club
        every {
            applicationEventPublisher.publishEvent(
                SendMessageEvent(
                    user = clubMember.user,
                    title = "부장 위임",
                    content = "${club.name}의 부장으로 위임되셨습니다.",
                    type = SendType.CLUB
                )
            )
        } just Runs
        val request = DelegateHeadDto(uuid = member.id)
        `when`("서비스를 실행하면"){
            val response = delegateHeadServiceImpl.execute(1, request)
            then("동아리 멤버가 부장이 되야함"){
                response.head shouldBe member
            }
            then("부장은 동아리 멤버가 되야함"){
                response.clubMember shouldBe head
            }
        }
    }
})