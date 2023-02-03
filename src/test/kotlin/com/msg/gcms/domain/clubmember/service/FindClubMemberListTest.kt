package com.msg.gcms.domain.clubmember.service

import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.util.impl.ClubMemberConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import org.springframework.context.annotation.Bean

class FindClubMemberListTest : BehaviorSpec({
    @Bean
    fun clubMemberConverter(): ClubMemberConverter {
        return ClubMemberConverterImpl()
    }
   val findClubMemberListService = mockk<FindClubMemberListService>(relaxed = true)

    Given("Test") {
        val user = TestUtils.data().user().entity()
        val club = TestUtils.data().club().entity()
        val clubMember = (1..2)
            .map { TestUtils.data().clubMember().entity(club, user) }
        val clubMemberDto = clubMember
            .map { clubMemberConverter().toDto(it, MemberScope.MEMBER) }
        val clubMemberListDto = ClubMemberListDto(MemberScope.MEMBER, clubMemberDto)
        When("is invoked") {
            val result: ClubMemberListDto = findClubMemberListService.execute(club.id)
            then("result should not be null") {
                result shouldNotBe null
            }
            then("result") {
                result shouldBe clubMemberListDto
            }
        }

    }
})