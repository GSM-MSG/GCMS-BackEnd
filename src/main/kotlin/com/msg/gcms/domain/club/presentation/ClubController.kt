package com.msg.gcms.domain.club.presentation

import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.service.CreateClubService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/club")
class ClubController(
    private val createClubService: CreateClubService,
) {
    @PostMapping
    fun createClub(@RequestBody createClubRequest: CreateClubRequest): ResponseEntity<Void> =
        createClubRequest.toDto()
            .let { createClubService.execute(it) }
            .let { ResponseEntity(HttpStatus.CREATED) }
}