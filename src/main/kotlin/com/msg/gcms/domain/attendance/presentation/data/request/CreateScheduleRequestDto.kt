package com.msg.gcms.domain.attendance.presentation.data.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.msg.gcms.domain.attendance.domain.enums.Period
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateScheduleRequestDto(
    @field:NotBlank
    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,

    @field:NotNull
    @field:NotEmpty
    val periods: List<Period>
)