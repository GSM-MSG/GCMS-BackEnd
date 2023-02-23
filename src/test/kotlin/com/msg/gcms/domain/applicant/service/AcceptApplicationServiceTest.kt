package com.msg.gcms.domain.applicant.service

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.repository.ApplicantRepository
import com.msg.gcms.domain.applicant.service.impl.AcceptApplicantServiceImpl
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class AcceptApplicationServiceTest : BehaviorSpec({

    val clubRepository = mockk<ClubRepository>()
    val applicantRepository = mockk<ApplicantRepository>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val userRepository = mockk<UserRepository>()
    val applicantConverter = mockk<ApplicantConverter>()

    val acceptApplicantService = AcceptApplicantServiceImpl(
        clubRepository = clubRepository,
        applicantConverter = applicantConverter,
        applicantRepository = applicantRepository,
        clubMemberRepository = clubMemberRepository,
        userRepository = userRepository
    )

    given("동아리 ID와 유저가 주어졌을때") {
        val user = TestUtils.data().user().entity()
        val tempClub = TestUtils.TestDataUtil.club().entity()
        val applicant = Applicant(1, tempClub, user)
        val club = TestUtils.TestDataUtil.club().entity(tempClub, applicant)

        val acceptDto = AcceptDto(
            uuid = user.id.toString()
        )

        every { clubRepository.findByIdOrNull(1) } returns club
        every { userRepository.findByIdOrNull(UUID.fromString(acceptDto.uuid)) } returns user
        val clubMember = ClubMember(
            club = club,
            user = user
        )

        every { applicantConverter.toEntity(club, user) } returns clubMember
        every { clubMemberRepository.save(clubMember) } returns clubMember
        every {
            applicantRepository.findByUserIdAndClub(
                userId = UUID.fromString(acceptDto.uuid),
                club = club
            )
        } returns applicant

        every { applicantRepository.delete(applicant) } returns Unit

        `when`("서비스를 실행하면") {
            acceptApplicantService.execute(1, acceptDto)
            then("delete가 실행되어야 함") {
                verify(exactly = 1) { applicantRepository.delete(applicant) }
            }
            then("insert가 실행되어야 함") {
                verify(exactly = 1) { clubMemberRepository.save(clubMember) }
            }
        }
    }

})