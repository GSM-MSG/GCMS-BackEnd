package com.msg.gcms.domain.attendance.presentation.data.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class CreateScheduleRequestDto(
    @field:NotBlank
    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate
)