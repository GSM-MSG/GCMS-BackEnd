package com.msg.gcms.domain.applicant.controller

import com.msg.gcms.domain.applicant.presentation.ApplicantController
import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import com.msg.gcms.domain.applicant.service.*
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class AcceptApplicationControllerTest : BehaviorSpec({
    val clubApplyService = mockk<ClubApplyService>()
    val cancelApplicationService = mockk<CancelApplicationService>()
    val applicantListService = mockk<ApplicantListService>()
    val applicantConverter = mockk<ApplicantConverter>()
    val acceptApplicationService = mockk<AcceptApplicantService>()
    val rejectApplicantService = mockk<RejectApplicantService>()
    val applicantController = ApplicantController(
        clubApplyService = clubApplyService,
        cancelApplicationService = cancelApplicationService,
        acceptApplicantService = acceptApplicationService,
        applicantListService = applicantListService,
        applicantConverter = applicantConverter,
        rejectApplicantService = rejectApplicantService
    )

    given("요청이 들어오면") {
        `when`("is received") {
            val acceptDto = AcceptDto(
                uuid = "thisIsUUID"
            )
            val acceptRequestDto = AcceptRequestDto(
                uuid = "thisIsUUID"
            )
            every { applicantConverter.toDto(acceptRequestDto) } returns acceptDto
            every { acceptApplicationService.execute(1, acceptDto) } returns Unit
            val response = applicantController.acceptApplicant(1, acceptRequestDto)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { acceptApplicationService.execute(1, AcceptDto(uuid = "thisIsUUID")) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.CREATED
            }
        }
    }
})