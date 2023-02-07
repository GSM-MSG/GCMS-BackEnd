package com.msg.gcms.domain.clubMember.presentation

import com.msg.gcms.domain.clubMember.presentation.data.request.DelegateHeadRequest
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.service.DelegateHeadService
import com.msg.gcms.domain.clubMember.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.service.impl.FindClubMemberListServiceImpl
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/club-member")
class ClubMemberController(
    private val findClubMemberListService: FindClubMemberListService,
    private val delegateHeadService: DelegateHeadService,
    private val clubMemberConverter: ClubMemberConverter,
) {

    @GetMapping("/{club_id}")
    fun findClubMemberList(@PathVariable("club_id") clubId: Long): ResponseEntity<ClubMemberListResponse> =
        findClubMemberListService.execute(clubId)
            .let { ResponseEntity.ok().body(clubMemberConverter.toResponse(it)) }

    @PatchMapping("/{club_id}")
    fun delegateHead(@PathVariable club_id: Long, @RequestBody delegateHeadRequest: DelegateHeadRequest): ResponseEntity<Void> =
        clubMemberConverter.toDto(delegateHeadRequest)
            .let { delegateHeadService.execute(club_id, it) }
            .let { ResponseEntity.noContent().build() }

}