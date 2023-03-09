package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.applicant.exception.AlreadyClubMemberException
import com.msg.gcms.domain.applicant.exception.DuplicateClubTypeApplicantException
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.impl.ClubApplyServiceImpl
import com.msg.gcms.domain.applicant.util.ApplicantSaveUtil
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.global.util.MessageSendUtil
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull

class ClubApplyServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val clubRepository = mockk<ClubRepository>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val applicantRepository = mockk<ApplicantRepository>()
    val applicantSaveUtil = mockk<ApplicantSaveUtil>()
    val messageSendUtil = mockk<MessageSendUtil>()

    val clubApplyServiceImpl = ClubApplyServiceImpl(userUtil, clubRepository, clubMemberRepository, applicantSaveUtil, applicantRepository, messageSendUtil)

    given("유저와 동아리가 주어지고"){
        val user = TestUtils.TestDataUtil.user().entity()
        val club = TestUtils.TestDataUtil.club().entity()
        val applicant = Applicant(1, club, user)
        init(clubRepository, club, userUtil, user, clubMemberRepository, applicantRepository, applicantSaveUtil, applicant)
        `when`("서비스를 실행하면"){
            val result = clubApplyServiceImpl.execute(club.id)
            then("결과값은 동일한 동아리와 유저를 가지고 있어야한다"){
                result.applicant.club shouldBe club
                result.applicant.user shouldBe user
            }
        }
        `when`("유저가 동아리의 부장이라면"){
            val club = TestUtils.TestDataUtil.club().entity(user)
            every { clubRepository.findByIdOrNull(club.id) } returns club
            every { clubMemberRepository.findByUserAndClub(user, club) } returns null
            then("AlreadyClubMemberException이 터져야 한다"){
                shouldThrow<AlreadyClubMemberException> {
                    clubApplyServiceImpl.execute(club.id)
                }
            }
        }
        init(clubRepository, club, userUtil, user, clubMemberRepository, applicantRepository, applicantSaveUtil, applicant)
        `when`("유저가 동아리 소속이라면"){
            val tempClub = TestUtils.TestDataUtil.club().entity(user)
            val clubMember = ClubMember(1, tempClub, user)
            val club = TestUtils.TestDataUtil.club().entity(tempClub, listOf(clubMember))
            every { clubRepository.findByIdOrNull(club.id) } returns club
            every { clubMemberRepository.findByUserAndClub(user, club) } returns clubMember
            then("AlreadyClubMemberException이 터져야 한다"){
                shouldThrow<AlreadyClubMemberException> {
                    clubApplyServiceImpl.execute(club.id)
                }
            }
        }
        init(clubRepository, club, userUtil, user, clubMemberRepository, applicantRepository, applicantSaveUtil, applicant)
        `when`("동아리를 못찾았으면"){
            every { clubRepository.findByIdOrNull(club.id) } returns null
            then("ClubNotFoundException이 터져야 한다"){
                shouldThrow<ClubNotFoundException> {
                    clubApplyServiceImpl.execute(club.id)
                }
            }
        }
        init(clubRepository, club, userUtil, user, clubMemberRepository, applicantRepository, applicantSaveUtil, applicant)
        `when`("같은 타입의 동아리에 신청했으면"){
            every { applicantRepository.countByClubTypeAndUser(club.type, user) } returns 1
            then("DuplicateClubTypeApplicantException"){
                shouldThrow<DuplicateClubTypeApplicantException> {
                    clubApplyServiceImpl.execute(club.id)
                }
            }
        }
    }
})

private fun init(
    clubRepository: ClubRepository,
    club: Club,
    userUtil: UserUtil,
    user: User,
    clubMemberRepository: ClubMemberRepository,
    applicantRepository: ApplicantRepository,
    applicantSaveUtil: ApplicantSaveUtil,
    applicant: Applicant
) {
    every { clubRepository.findByIdOrNull(club.id) } returns club
    every { userUtil.fetchCurrentUser() } returns user
    every { clubMemberRepository.findByUserAndClub(user, club) } returns null
    every { applicantRepository.countByClubTypeAndUser(club.type, user) } returns 0
    every { applicantSaveUtil.saveApplicant(club, user) } returns applicant
}