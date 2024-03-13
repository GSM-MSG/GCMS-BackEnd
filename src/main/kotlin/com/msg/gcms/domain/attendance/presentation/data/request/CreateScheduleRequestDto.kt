package com.msg.gcms.domain.attendance.presentation.data.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateScheduleRequestDto(
    @NotBlank
    val name: String,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val date: LocalDate,

    @field:NotNull
    val periods: List<LocalTime>
)