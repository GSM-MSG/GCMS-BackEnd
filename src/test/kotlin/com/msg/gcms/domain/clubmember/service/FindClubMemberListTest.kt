package com.msg.gcms.domain.clubmember.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.service.impl.FindClubMemberListServiceImpl
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.util.impl.ClubMemberConverterImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.findByIdOrNull

class FindClubMemberListTest : BehaviorSpec({
    @Bean
    fun clubMemberConverter(): ClubMemberConverter {
        return ClubMemberConverterImpl()
    }

    val userUtil = mockk<UserUtil>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val clubRepository = mockk<ClubRepository>()
    val findClubMemberListService = FindClubMemberListServiceImpl(clubMemberRepository, clubRepository, clubMemberConverter(), userUtil)

    Given("유저, 동아리, 동아리멤버가 주어졌을때") {
        val user = TestUtils.data().user().entity()
        val club = TestUtils.data().club().entity()
        val clubMember: List<ClubMember> = (1..2)
            .map { TestUtils.data().clubMember().entity(club, user) }
        val clubEntity = TestUtils.data().club().entity(club, clubMember)
        val clubMemberDto: MutableList<ClubMemberDto> = clubMember
            .map { clubMemberConverter().toDto(it, MemberScope.MEMBER) }.toMutableList()
        val clubHeadDto = clubMemberConverter().toDto(club)
        clubMemberDto.add(clubHeadDto)
        val clubMemberListDto = ClubMemberListDto(MemberScope.MEMBER, clubMemberDto)

        When("동아리 구성원이 클럽 ID로 동아리 구성원 리스트를 조회할 때") {
            every { userUtil.fetchCurrentUser() } returns user
            every { clubRepository.findByIdOrNull(club.id) } returns clubEntity
            every { clubMemberRepository.findAllByClub(clubEntity) } returns clubMember
            val result: ClubMemberListDto = findClubMemberListService.execute(club.id)
            then("result 결과값이 null이 아니어야 한다.") {
                result shouldNotBe null
            }
            then("result 값과 ClubMemberListDto 값이 동일해야 한다.") {
                result shouldBe clubMemberListDto
            }
        }

    }
})