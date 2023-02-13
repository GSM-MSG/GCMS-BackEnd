package com.msg.gcms.domain.applicant.presentation

import com.msg.gcms.domain.applicant.service.CancelApplicationService
import com.msg.gcms.domain.applicant.service.ClubApplyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/applicant")
class ApplicantController(
    private val clubApplyService: ClubApplyService,
    private val cancelApplicationService: CancelApplicationService,
) {
    @PostMapping("/{club_id}")
    fun apply(@PathVariable club_id: Long): ResponseEntity<Void> =
        clubApplyService.execute(club_id)
            .run { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}")
    fun cancelApplication(@PathVariable club_id: Long): ResponseEntity<Void> =
        cancelApplicationService.execute(club_id)
            .run { ResponseEntity.noContent().build() }
}