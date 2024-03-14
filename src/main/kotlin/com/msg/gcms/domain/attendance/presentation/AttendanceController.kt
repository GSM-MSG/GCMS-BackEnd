package com.msg.gcms.domain.attendance.presentation

import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusDto
import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.attendance.service.CreateScheduleService
import com.msg.gcms.domain.attendance.service.QueryCurrentAttendConditionService
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import javax.validation.Valid

@RequestController("/attend")
class AttendanceController(
    private val createScheduleService: CreateScheduleService,
    private val queryCurrentAttendConditionService: QueryCurrentAttendConditionService,
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

    @GetMapping("/{club_id}")
    fun queryCurrentAttendCondition(
        @PathVariable("club_id") clubId: Long,
        @RequestParam(required = false) date: LocalDate?,
        @RequestParam(required = false) period: LocalTime?
    ): ResponseEntity<List<UserAttendanceStatusDto>> =
        scheduleConverter.toDto(clubId, date, period)
            .let { queryCurrentAttendConditionService.execute(it) }
            .let { ResponseEntity.ok(it) }
}