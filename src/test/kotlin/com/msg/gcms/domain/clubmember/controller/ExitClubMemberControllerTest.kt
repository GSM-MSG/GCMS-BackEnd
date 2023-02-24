package com.msg.gcms.domain.clubmember.controller

import com.msg.gcms.domain.clubMember.presentation.ClubMemberController
import com.msg.gcms.domain.clubMember.presentation.data.dto.ChangeHeadDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberExitDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto
import com.msg.gcms.domain.clubMember.presentation.data.request.DelegateHeadRequest
import com.msg.gcms.domain.clubMember.presentation.data.request.ExitClubMemberRequest
import com.msg.gcms.domain.clubMember.service.DelegateHeadService
import com.msg.gcms.domain.clubMember.service.ExitClubMemberService
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.util.impl.ClubMemberConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class ExitClubMemberControllerTest : BehaviorSpec({
    @Bean
    fun clubMemberConverter(): ClubMemberConverter = ClubMemberConverterImpl()

    val findClubMemberListService = mockk<FindClubMemberListService>()
    val delegateHeadService = mockk<DelegateHeadService>()
    val exitClubMemberService = mockk<ExitClubMemberService>()
    val clubMemberController = ClubMemberController(findClubMemberListService, delegateHeadService, clubMemberConverter(), exitClubMemberService)

    given("요청이 들어오면") {
        val user = TestUtils.data().user().entity()
        val club = TestUtils.data().club().entity()
        val clubMemberExitDto = ClubMemberExitDto(club.id, user.id.toString())
        val exitClubMemberRequest = ExitClubMemberRequest(user.id.toString())
        `when`("is received") {
            every { exitClubMemberService.execute(clubMemberExitDto) } returns Unit
            val response = clubMemberController.exitClubMember(club.id, exitClubMemberRequest)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { exitClubMemberService.execute(clubMemberExitDto) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})