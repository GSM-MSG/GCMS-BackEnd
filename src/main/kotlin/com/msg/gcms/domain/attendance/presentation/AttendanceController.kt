package com.msg.gcms.domain.attendance.presentation

import com.msg.gcms.domain.attendance.domain.enums.Period
import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusListDto
import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusBatchRequestDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusRequestDto
import com.msg.gcms.domain.attendance.service.*
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URL
import java.net.URLEncoder
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RequestController("/attend")
class AttendanceController(
    private val createScheduleService: CreateScheduleService,
    private val queryCurrentAttendConditionService: QueryCurrentAttendConditionService,
    private val updateAttendanceStatusService: UpdateAttendanceStatusService,
    private val updateAttendanceStatusBatchService: UpdateAttendanceStatusBatchService,
    private val clubAttendanceStatusExcelService: ClubAttendanceStatusExcelService,
    private val scheduleConverter: ScheduleConverter,
    private val attendanceConverter: AttendanceConverter
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
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
        @RequestParam(required = false) period: Period?
    ): ResponseEntity<UserAttendanceStatusListDto> =
        scheduleConverter.toDto(clubId, date, period)
            .let { queryCurrentAttendConditionService.execute(it) }
            .let { ResponseEntity.ok(it) }

    @PatchMapping
    fun updateAttendanceStatus(
        @RequestBody @Valid updateAttendanceStatusRequestDto: UpdateAttendanceStatusRequestDto
    ): ResponseEntity<Unit> =
        attendanceConverter.toDto(updateAttendanceStatusRequestDto)
            .let { updateAttendanceStatusService.execute(it) }
            .let { ResponseEntity.noContent().build() }


    @PatchMapping("/batch")
    fun updateAttendanceStatusBatch(
        @RequestBody @Valid updateAttendanceStatusBatchRequestDto: UpdateAttendanceStatusBatchRequestDto
    ): ResponseEntity<Unit> =
        attendanceConverter.toDto(updateAttendanceStatusBatchRequestDto)
            .let { updateAttendanceStatusBatchService.execute(it) }
            .let { ResponseEntity.noContent().build() }

    @GetMapping("/excel")
    fun clubAttendanceStatusExcel(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) currentDate: LocalDate, response: HttpServletResponse): ByteArray {
        response.setHeader("Content-Disposition", "attachment; filename=${URLEncoder.encode("$currentDate 출석부", "UTF-8").replace("+", "%20")}.xlsx")
        return clubAttendanceStatusExcelService.execute(currentDate)
    }
}