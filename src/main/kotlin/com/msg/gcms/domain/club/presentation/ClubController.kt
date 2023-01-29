package com.msg.gcms.domain.club.presentation

import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.service.CreateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/club")
class ClubController(
    private val createClubService: CreateClubService,
    private val clubConverter: ClubConverter
) {
    @PostMapping
    fun createClub(@Valid @RequestBody createClubRequest: CreateClubRequest): ResponseEntity<Void> =
        clubConverter.toDto(createClubRequest)
            .let { createClubService.execute(it) }
            .let { ResponseEntity(HttpStatus.CREATED) }
}