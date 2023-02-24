package com.msg.gcms.domain.clubmember.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.exception.ClubMemberExitOneSelfException
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberExitDto
import com.msg.gcms.domain.clubMember.service.impl.ExitClubMemberServiceImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull

class ExitClubMemberTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val clubRepository = mockk<ClubRepository>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val exitClubMemberService = ExitClubMemberServiceImpl(userUtil, clubRepository, clubMemberRepository)

    Given("clubMemberExitDto 주어졌을때") {
        val user = (1..2)
            .map { TestUtils.data().user().entity() }
        val club = TestUtils.data().club().entity()
        val clubMember: List<ClubMember> = user
            .map { TestUtils.data().clubMember().entity(club, it) }
        var clubMemberExitDto = ClubMemberExitDto(clubId = club.id, user[0].id.toString())
        every { userUtil.fetchCurrentUser() } returns club.user
        When("해당 동아리에 멤버를 삭제 할 때") {
            every { clubRepository.findByIdOrNull(club.id) } returns club
            every { clubMemberRepository.findByClub(club) } returns clubMember
            every { clubMemberRepository.delete(clubMember[0]) } returns Unit
            exitClubMemberService.execute(clubMemberExitDto)
            Then("그때 delete쿼리가 실행되어야함"){
                verify(exactly = 1){ clubMemberRepository.delete(clubMember[0]) }
            }
        }

        When("자기 자신을 지울려할때"){
            clubMemberExitDto = ClubMemberExitDto(clubId = club.id, club.user.id.toString())
            Then("ClubMemberExitOneSelfException이 터져야함"){
                shouldThrow<ClubMemberExitOneSelfException> {
                    exitClubMemberService.execute(clubMemberExitDto)
                }
            }
        }

        When("부장이 아닌 사용자가 삭제요청을 호출할때"){
            every { userUtil.fetchCurrentUser() } returns user[0]
            clubMemberExitDto = ClubMemberExitDto(clubId = club.id, club.user.id.toString())
            Then("HeadNotSameException이 터져야함"){
                shouldThrow<HeadNotSameException> {
                    exitClubMemberService.execute(clubMemberExitDto)
                }
            }
        }

    }
})