package com.msg.gcms.domain.clubMember.presentation

import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/club-member")
class ClubMemberController(
    private val findClubMemberListService: FindClubMemberListService,
    private val clubMemberConverter: ClubMemberConverter,
) {

    @GetMapping("/{club-id}")
    fun findAllMemberFromClubId(@PathVariable("club-id") clubId: Long): ResponseEntity<ClubMemberListResponse> =
        findClubMemberListService.execute(clubId)
            .let { ResponseEntity.ok(clubMemberConverter.toResponse(it)) }
}