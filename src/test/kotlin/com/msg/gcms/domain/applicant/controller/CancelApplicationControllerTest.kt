package com.msg.gcms.domain.applicant.controller

import com.msg.gcms.domain.applicant.presentation.ApplicantController
import com.msg.gcms.domain.applicant.service.*
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class CancelApplicationControllerTest : BehaviorSpec({

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
            every { cancelApplicationService.execute(1) } returns Unit
            val response = applicantController.cancelApplication(1)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { cancelApplicationService.execute(1) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})