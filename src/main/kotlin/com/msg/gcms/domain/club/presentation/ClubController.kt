package com.msg.gcms.domain.club.presentation

import com.msg.gcms.domain.club.domain.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.service.CreateClubService
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.service.UpdateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.validation.Valid
import com.msg.gcms.domain.club.service.CloseClubService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/club")
private class ClubController(
    private val createClubService: CreateClubService,
    private val findClubListService: FindClubListService,
    private val updateClubService: UpdateClubService,
    private val closeClubService: CloseClubService,
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

    @PatchMapping("/{club_id}")
    fun updateClubById(@PathVariable club_id: Long, @Valid @RequestBody updateClubRequest: UpdateClubRequest): ResponseEntity<Void> =
        clubConverter.toDto(updateClubRequest)
            .let { updateClubService.execute(club_id, it) }
            .let { ResponseEntity.noContent().build() }

    @PatchMapping("/{club_id}/close")
    fun closeClub(@PathVariable club_id: Long): ResponseEntity<Void> =
        closeClubService.execute(club_id)
            .let { ResponseEntity.noContent().build() }
}