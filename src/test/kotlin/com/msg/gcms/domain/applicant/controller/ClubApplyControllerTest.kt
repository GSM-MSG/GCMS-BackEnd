package com.msg.gcms.domain.applicant.controller

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.applicant.presentation.ApplicantController
import com.msg.gcms.domain.applicant.presentation.data.dto.ClubApplyDto
import com.msg.gcms.domain.applicant.service.ClubApplyService
import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubStatusDto
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class ClubApplyControllerTest : BehaviorSpec({

    val clubApplyService = mockk<ClubApplyService>()
    val applicantController = ApplicantController(
        clubApplyService
    )

    given("요청이 들어오면") {
        `when`("is received") {
            every { clubApplyService.execute(1) } returns ClubApplyDto(Applicant(1, TestUtils.data().club().entity(), TestUtils.data().user().entity()))
            val response = applicantController.apply(1)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { clubApplyService.execute(1) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})