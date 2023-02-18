//package com.msg.gcms.domain.clubmember.service
//
//import com.msg.gcms.domain.club.domain.repository.ClubRepository
//import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
//import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
//import com.msg.gcms.domain.clubMember.service.impl.ExitClubMemberServiceImpl
//import com.msg.gcms.global.util.UserUtil
//import com.msg.gcms.testUtils.TestUtils
//import io.kotest.core.spec.style.BehaviorSpec
//import io.mockk.every
//import io.mockk.mockk
//import org.springframework.data.repository.findByIdOrNull
//
//class ExitClubMemberTest : BehaviorSpec({
//    val userUtil = mockk<UserUtil>()
//    val clubRepository = mockk<ClubRepository>()
//    val clubMemberRepository = mockk<ClubMemberRepository>()
//    val exitClubMemberService = ExitClubMemberServiceImpl(userUtil, clubRepository, clubMemberRepository)
//
//    Given("clubMemberExitDto 주어졌을때") {
//        val user = (1..2)
//            TestUtils.data().user().entity()
//        val club = TestUtils.data().club().entity()
//        val clubMember: List<ClubMember> = user
//            .map { TestUtils.data().clubMember().entity(club, ) }
//        When("해당 동아리에 멤버를 삭제 할 때") {
//            every { clubRepository.findByIdOrNull(club.id) } returns club
//            every { clubMemberRepository.findByClub(club) } returns clubMember
//        }
//    }
//})