package com.msg.gcms.domain.applicant.presentation

import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import com.msg.gcms.domain.applicant.service.AcceptApplicantService
import com.msg.gcms.domain.applicant.service.CancelApplicationService
import com.msg.gcms.domain.applicant.service.ClubApplyService
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/applicant")
class ApplicantController(
    private val clubApplyService: ClubApplyService,
    private val cancelApplicationService: CancelApplicationService,
    private val acceptApplicantService: AcceptApplicantService,
    private val applicantConverter: ApplicantConverter
) {
    @PostMapping("/{club_id}")
    fun apply(@PathVariable club_id: Long): ResponseEntity<Void> =
        clubApplyService.execute(club_id)
            .run { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}")
    fun cancelApplication(@PathVariable club_id: Long): ResponseEntity<Void> =
        cancelApplicationService.execute(club_id)
            .run { ResponseEntity.noContent().build() }

    @PostMapping("/{club_id}/accept")
    fun acceptApplicant(
        @PathVariable club_id: Long,
        @RequestBody acceptRequestDto: AcceptRequestDto
    ): ResponseEntity<Void> {
        applicantConverter.toDto(acceptRequestDto = acceptRequestDto)
            .let { acceptApplicantService.execute(club_id, it) }
            .run { return ResponseEntity.noContent().build() }
    }

}