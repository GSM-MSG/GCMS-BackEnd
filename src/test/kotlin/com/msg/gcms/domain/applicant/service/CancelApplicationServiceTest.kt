package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.impl.CancelApplicationServiceImpl
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.global.event.SendMessageEvent
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull

class CancelApplicationServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val applicantRepository = mockk<ApplicantRepository>()
    val clubRepository = mockk<ClubRepository>()
    val applicationEventPublisher = mockk<ApplicationEventPublisher>()
    val cancelApplicationServiceImpl = CancelApplicationServiceImpl(clubRepository, applicantRepository, userUtil, applicationEventPublisher)

    given("유저, 신청된 동아리가 주어지고"){
        val user = TestUtils.TestDataUtil.user().entity()
        val tempClub = TestUtils.TestDataUtil.club().entity()
        val applicant = Applicant(1, tempClub, user)
        val club = TestUtils.TestDataUtil.club().entity(tempClub, applicant)

        every { clubRepository.findByIdOrNull(club.id) } returns club
        every { applicantRepository.delete(applicant) } returns Unit
        every { userUtil.fetchCurrentUser() } returns user
        every {
            applicationEventPublisher.publishEvent(
                SendMessageEvent(
                    user = club.user,
                    title = "동아리 신청 취소",
                    content = "${user.nickname}님이 ${club.name}에 신청 취소했습니다.",
                    type = SendType.CLUB
                )
            )
        } just Runs

        `when`("서비스를 실행하면"){
            cancelApplicationServiceImpl.execute(club.id)
            then("delete가 실행되어야함"){
                verify(exactly = 1) { applicantRepository.delete(applicant) }
            }
        }
        `when`("동아리를 찾지 못한다면"){
            every { clubRepository.findByIdOrNull(club.id) } returns null
            then("ClubNotFoundException이 터져야함"){
                shouldThrow<ClubNotFoundException> {
                    cancelApplicationServiceImpl.execute(club.id)
                }
            }
        }
    }

})