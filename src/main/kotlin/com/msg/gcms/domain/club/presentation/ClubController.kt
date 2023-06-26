package com.msg.gcms.domain.club.presentation

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.request.CreateOpeningApplicationRequest
import com.msg.gcms.domain.club.presentation.data.request.CreateClubRequest
import com.msg.gcms.domain.club.presentation.data.request.CreateOperationPlanRequest
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.club.presentation.data.response.DetailClubResponseDto
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.OpeningApplicationConverter
import com.msg.gcms.domain.club.utils.OperationPlanConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestController("/club")
class ClubController(
    private val createClubService: CreateClubService,
    private val findClubListService: FindClubListService,
    private val updateClubService: UpdateClubService,
    private val closeClubService: CloseClubService,
    private val openClubService: OpenClubService,
    private val exitClubService: ExitClubService,
    private val deleteClubService: DeleteClubService,
    private val detailClubService: DetailClubService,
    private val createOperationPlanService: CreateOperationPlanService,
    private val createOpeningApplicationService: CreateOpeningApplicationService,
    private val clubConverter: ClubConverter,
    private val operationPlanConverter: OperationPlanConverter,
    private val openingApplicationConverter: OpeningApplicationConverter
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

    @GetMapping("/{club_id}")
    fun detailClub(@PathVariable("club_id") clubId: Long): ResponseEntity<DetailClubResponseDto> {
        val result = detailClubService.execute(clubId)
        val memberResponseDto = result.member
            .map { clubConverter.toResponseDto(it) }
        val responseDto = clubConverter.toResponseDto(result, memberResponseDto)
        return ResponseEntity.ok().body(responseDto)
    }

    @PatchMapping("/{club_id}")
    fun updateClubById(@PathVariable("club_id") clubId: Long, @Valid @RequestBody updateClubRequest: UpdateClubRequest): ResponseEntity<Void> =
        clubConverter.toDto(updateClubRequest)
            .let { updateClubService.execute(clubId , it) }
            .let { ResponseEntity.noContent().build() }

    @PatchMapping("/{club_id}/close")
    fun closeClub(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        closeClubService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @PatchMapping("/{club_id}/open")
    fun openClub(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        openClubService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}/exit")
    fun exitClub(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        exitClubService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @DeleteMapping("/{club_id}")
    fun deleteClub(@PathVariable("club_id") clubId: Long): ResponseEntity<Void> =
        deleteClubService.execute(clubId)
            .let { ResponseEntity.noContent().build() }

    @PostMapping("/operation/{club_id}")
    fun createOperationPlan(@PathVariable("club_id") clubId: Long, createOperationPlanRequest: CreateOperationPlanRequest): ResponseEntity<Void> =
        operationPlanConverter.toDto(createOperationPlanRequest)
            .let { createOperationPlanService.execute(clubId, it) }
            .let { ResponseEntity(HttpStatus.CREATED) }

    @PostMapping("/application/{club_id}")
    fun createClubOpeningApplication(@PathVariable("club_id") clubId: Long, createOpeningApplicationRequest: CreateOpeningApplicationRequest) : ResponseEntity<Void> =
        openingApplicationConverter.toDto(createOpeningApplicationRequest)
            .let { createOpeningApplicationService.execute(clubId, it) }
            .let { ResponseEntity(HttpStatus.CREATED) }
}