package com.msg.gcms.domain.clubmember.controller

import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.ClubMemberController
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.util.impl.ClubMemberConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class FindClubMemberListControllerTest : BehaviorSpec({
    @Bean
    fun clubMemberConverter(): ClubMemberConverter = ClubMemberConverterImpl()

    val findClubMemberListService = mockk<FindClubMemberListService>()
    val clubMemberController = ClubMemberController(findClubMemberListService, clubMemberConverter())

    Given("요청이 주어졌을때") {
        val clubId: Long = 1L
        val user = (1..3)
            .map { TestUtils.data().user().entity() }
        val clubMember = user
            .map { TestUtils.data().clubMember().clubMemberDto(it, MemberScope.MEMBER) }
        val clubMemberListDto = ClubMemberListDto(MemberScope.MEMBER, clubMember)
        val responseDto = ClubMemberListResponse(MemberScope.MEMBER, clubMember)

        When("is received") {
            every { findClubMemberListService.execute(clubId) } returns clubMemberListDto
            val response = clubMemberController.findClubMemberList(clubId)
            val body = response.body
            then("body가 null이 아니어야 한다.") {
                body shouldNotBe null
            }

            then("findClubMemberListService가 한번은 실행되어야 한다.") {
                verify(exactly = 1) { findClubMemberListService.execute(clubId) }
            }
            then("응답 상태코드가 OK여야 한다.") {
                response.statusCode shouldBe HttpStatus.OK
            }
            then("body값이랑 responseDto값이 같게 해야 한다.") {
                body shouldBe responseDto
            }
        }
    }
}) {
}