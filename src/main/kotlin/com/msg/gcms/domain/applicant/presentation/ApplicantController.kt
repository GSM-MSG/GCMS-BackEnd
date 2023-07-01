package com.msg.gcms.domain.applicant.presentation

import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import com.msg.gcms.domain.applicant.presentation.data.request.RejectRequestDto
import com.msg.gcms.domain.applicant.presentation.data.response.ApplicantListResponseDto
import com.msg.gcms.domain.applicant.service.*
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/applicant")
class ApplicantController(
    private val clubApplyService: ClubApplyService,
    private val cancelApplicationService: CancelApplicationService,
    private val acceptApplicantService: AcceptApplicantService,
    private val applicantListService: ApplicantListService,
    private val applicantConverter: ApplicantConverter,
    private val rejectApplicantService: RejectApplicantService
) {
    @GetMapping("/{club_id}")
    fun findApplicantListByClubId(@PathVariable("club_id") clubId: Long): ResponseEntity<ApplicantListResponseDto> {
        val result = applicantListService.execute(clubId)
        val applicantResponse = result.applicantList
            .map { applicantConverter.toResponseDto(it) }
        val response = applicantConverter.toResponseDto(result, applicantResponse)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/{club_id}")
    fun apply(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        clubApplyService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}")
    fun cancelApplication(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        cancelApplicationService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @PostMapping("/{club_id}/accept")
    fun acceptApplicant(
        @PathVariable("club_id") clubId: Long,
        @RequestBody acceptRequestDto: AcceptRequestDto
    ): ResponseEntity<Void> =
        applicantConverter.toDto(acceptRequestDto = acceptRequestDto)
            .let { acceptApplicantService.execute(clubId, it) }
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }


    @PostMapping("/{club_id}/reject")
    fun rejectApplicant(
        @PathVariable("club_id") clubId: Long,
        @RequestBody rejectRequestDto: RejectRequestDto
    ): ResponseEntity<Void> =
        applicantConverter.toDto(rejectRequestDto)
            .let { rejectApplicantService.execute(clubId, it) }
            .run { ResponseEntity.noContent().build() }

}