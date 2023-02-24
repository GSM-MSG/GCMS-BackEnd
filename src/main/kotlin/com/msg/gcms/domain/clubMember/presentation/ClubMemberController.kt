package com.msg.gcms.domain.clubMember.presentation

import com.msg.gcms.domain.clubMember.presentation.data.request.DelegateHeadRequest
import com.msg.gcms.domain.clubMember.presentation.data.request.ExitClubMemberRequest
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.service.DelegateHeadService
import com.msg.gcms.domain.clubMember.service.ExitClubMemberService
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestController("/club-member")
class ClubMemberController(
    private val findClubMemberListService: FindClubMemberListService,
    private val delegateHeadService: DelegateHeadService,
    private val clubMemberConverter: ClubMemberConverter,
    private val exitClubMemberService: ExitClubMemberService
) {

    @GetMapping("/{club_id}")
    fun findClubMemberList(@PathVariable("club_id") clubId: Long): ResponseEntity<ClubMemberListResponse> =
        findClubMemberListService.execute(clubId)
            .let { ResponseEntity.ok().body(clubMemberConverter.toResponse(it)) }

    @PostMapping("/{club_id}")
    fun exitClubMember(
        @PathVariable("club_id") clubId: Long,
        @RequestBody requestDto: ExitClubMemberRequest
    ): ResponseEntity<Void> =
        clubMemberConverter.toDto(clubId, requestDto)
            .let { exitClubMemberService.execute(it) }
            .let { ResponseEntity.noContent().build() }
    @PatchMapping("/{club_id}")
    fun delegateHead(@PathVariable club_id: Long, @RequestBody delegateHeadRequest: DelegateHeadRequest): ResponseEntity<Void> =
        clubMemberConverter.toDto(delegateHeadRequest)
            .let { delegateHeadService.execute(club_id, it) }
            .let { ResponseEntity.noContent().build() }

}