package com.msg.gcms.domain.applicant.controller

import com.msg.gcms.domain.applicant.presentation.ApplicantController
import com.msg.gcms.domain.applicant.presentation.data.dto.RejectDto
import com.msg.gcms.domain.applicant.presentation.data.request.RejectRequestDto
import com.msg.gcms.domain.applicant.service.*
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class RejectApplicantControllerTest : BehaviorSpec({
    val clubApplyService = mockk<ClubApplyService>()
    val cancelApplicationService = mockk<CancelApplicationService>()
    val applicantListService = mockk<ApplicantListService>()
    val applicantConverter = mockk<ApplicantConverter>()
    val acceptApplicationService = mockk<AcceptApplicantService>()
    val rejectApplicantService = mockk<RejectApplicantService>()
    val applicantController = ApplicantController(
        clubApplyService,
        cancelApplicationService,
        acceptApplicationService,
        applicantListService,
        applicantConverter,
        rejectApplicantService
    )

    given("요청이 들어오면") {
        `when`("is received") {
            val rejectRequestDto = RejectRequestDto(
                uuid = "thisIsUUID"
            )
            val rejectDto = RejectDto(
                uuid = "thisIsUUID"
            )
            every { applicantConverter.toDto(rejectRequestDto) } returns rejectDto
            every { rejectApplicantService.execute(1, rejectDto) } returns Unit
            val response = applicantController.rejectApplicant(1, rejectRequestDto)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { rejectApplicantService.execute(1, rejectDto) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})