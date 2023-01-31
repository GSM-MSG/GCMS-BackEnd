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
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.service.UpdateClubService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


@RestController
@RequestMapping("/club")
class ClubController(
    private val createClubService: CreateClubService,
    private val findClubListService: FindClubListService,
    private val updateClubService: UpdateClubService,
    private val clubConverter: ClubConverter
) {
    @PostMapping
    fun createClub(@Valid @RequestBody createClubRequest: CreateClubRequest): ResponseEntity<Void> =
        clubConverter.toDto(createClubRequest)
            .let { createClubService.execute(it) }
            .let { ResponseEntity(HttpStatus.CREATED) }
    @GetMapping
    fun findClubListByClubType(@RequestParam("type") type: ClubType): ResponseEntity<List<ClubListResponseDto>> =
        clubConverter.toDto(type)
            .let { findClubListService.execute(it) }
            .map { clubConverter.toResponseDto(it) }
            .let { ResponseEntity.ok().body(it) }

    @PatchMapping("/{clubId}")
    fun updateClubById(@PathVariable clubId: Long, @Valid @RequestBody updateClubRequest: UpdateClubRequest): ResponseEntity<Void> =
        clubConverter.toDto(updateClubRequest)
            .let { updateClubService.execute(clubId, it) }
            .let { ResponseEntity.noContent().build() }
}