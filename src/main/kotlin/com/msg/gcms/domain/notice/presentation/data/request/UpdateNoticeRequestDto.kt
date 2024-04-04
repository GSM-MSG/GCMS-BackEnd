package com.msg.gcms.domain.notice.presentation.data.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class UpdateNoticeRequestDto(
    @field:NotBlank
    @field:Length(max = 100)
    val title: String,

    @field:NotBlank
    @field:Length(max = 2000)
    val content: String
)