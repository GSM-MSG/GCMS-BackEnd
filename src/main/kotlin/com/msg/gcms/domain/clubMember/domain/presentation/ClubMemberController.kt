package com.msg.gcms.domain.clubMember.domain.presentation

import com.msg.gcms.domain.clubMember.domain.service.FindClubMemberListService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/club-member")
class ClubMemberController(
    private val findClubMemberListService: FindClubMemberListService
) {

    @GetMapping("{club-id}")
    fun findAllByClubId(@PathVariable("club-id") clubId: Long) {
        findClubMemberListService.execute(clubId)
    }
}