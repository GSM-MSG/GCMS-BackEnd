package com.msg.gcms.domain.attendance.presentation

import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.attendance.service.CreateScheduleService
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/attend")
class AttendanceController(
    private val createScheduleService: CreateScheduleService,
    private val scheduleConverter: ScheduleConverter
) {
    @PostMapping("/{club_id}/club")
    fun createSchedule(
        @PathVariable("club_id") clubId: Long,
        @RequestBody @Valid createScheduleRequestDto: CreateScheduleRequestDto
    ): ResponseEntity<Unit> =
        scheduleConverter.toDto(createScheduleRequestDto)
            .let { createScheduleService.execute(clubId, it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }
}