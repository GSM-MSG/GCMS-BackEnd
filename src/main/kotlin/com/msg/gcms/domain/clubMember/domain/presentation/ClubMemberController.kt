package com.msg.gcms.domain.clubMember.domain.presentation

import com.msg.gcms.domain.clubMember.domain.presentation.dto.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.domain.service.FindClubMemberListService
import com.msg.gcms.domain.clubMember.domain.util.ClubMemberConverter
import com.msg.gcms.global.util.UserUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/club-member")
class ClubMemberController(
    private val findClubMemberListService: FindClubMemberListService,
    private val clubMemberConverter: ClubMemberConverter
) {

    @GetMapping("{club-id}")
    fun findAllByClubId(@PathVariable("club-id") clubId: Long): ClubMemberListResponse =
    @GetMapping("/{club-id}")
        findClubMemberListService.execute(clubId)
            .let { clubMemberConverter.toResponse(it) }

}