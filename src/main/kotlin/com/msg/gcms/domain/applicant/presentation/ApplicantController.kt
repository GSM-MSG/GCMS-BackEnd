package com.msg.gcms.domain.applicant.presentation

import com.msg.gcms.domain.applicant.service.ClubApplyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/applicant")
class ApplicantController(
    private val clubApplyService: ClubApplyService
) {
    @PostMapping("/{club_id}")
    fun apply(@PathVariable club_id: Long): ResponseEntity<Void> =
        clubApplyService.execute(club_id)
            .run { ResponseEntity.noContent().build() }
}