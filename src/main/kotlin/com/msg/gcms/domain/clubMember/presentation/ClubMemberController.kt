package com.msg.gcms.domain.clubMember.presentation

import com.msg.gcms.domain.club.presentation.data.request.ClubIdRequestDto
import com.msg.gcms.domain.club.util.ClubConverter
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
    private val clubConverter: ClubConverter
) {

    @GetMapping("/{club-id}")
    fun findAllMemberFromClubId(@PathVariable("club-id") clubId: ClubIdRequestDto): ResponseEntity<ClubMemberListResponse> =
        clubConverter.toDto(clubId)
            .let { findClubMemberListService.execute(it) }
            .let { ResponseEntity.ok(clubMemberConverter.toResponse(it)) }
}