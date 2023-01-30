package com.msg.gcms.domain.club.presentation

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.utils.ClubConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/club")
class ClubController(
    private val clubConverter: ClubConverter,
    private val findClubListService: FindClubListService,
) {
    @GetMapping
    fun findClubListByClubType(@RequestParam("type") type: ClubType): ResponseEntity<List<ClubListResponseDto>> =
        clubConverter.toDto(type)
            .let { findClubListService.execute(it) }
            .map { clubConverter.toResponseDto(it) }
            .let { ResponseEntity.ok().body(it) }

}