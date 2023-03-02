package com.msg.gcms.domain.admin.presentation

import com.msg.gcms.domain.admin.service.AcceptClubService
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable

@RequestController("/admin")
class AdminController(
    private val acceptClubService: AcceptClubService
) {
    @PatchMapping("/{club_id}")
    fun acceptClub(@PathVariable("club_id") clubId: Long) : ResponseEntity<Void> =
        acceptClubService.execute(clubId = clubId)
            .let { ResponseEntity.noContent().build() }
}