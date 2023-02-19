package com.msg.gcms.domain.applicant.presentation

import com.msg.gcms.domain.applicant.presentation.data.response.ApplicantListResponseDto
import com.msg.gcms.domain.applicant.service.ApplicantListService
import com.msg.gcms.domain.applicant.service.CancelApplicationService
import com.msg.gcms.domain.applicant.service.ClubApplyService
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/applicant")
class ApplicantController(
    private val clubApplyService: ClubApplyService,
    private val cancelApplicationService: CancelApplicationService,
    private val applicantListService: ApplicantListService,
    private val applicantConverter: ApplicantConverter
) {
    @GetMapping("/{club_id}")
    fun findApplicantListByClubId(@PathVariable("club_id") clubId: Long):ResponseEntity<ApplicantListResponseDto> {
        val result = applicantListService.execute(clubId)
        val applicantResponse = result.applicantList
            .map { applicantConverter.toResponseDto(it) }
        val response = applicantConverter.toResponseDto(result, applicantResponse)
        return ResponseEntity.ok().body(response)
    }
    @PostMapping("/{club_id}")
    fun apply(@PathVariable club_id: Long): ResponseEntity<Void> =
        clubApplyService.execute(club_id)
            .run { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}")
    fun cancelApplication(@PathVariable club_id: Long): ResponseEntity<Void> =
        cancelApplicationService.execute(club_id)
            .run { ResponseEntity.noContent().build() }
}