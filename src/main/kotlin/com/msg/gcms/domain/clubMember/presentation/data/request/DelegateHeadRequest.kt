package com.msg.gcms.domain.clubMember.presentation.data.request

import java.util.UUID
import javax.validation.constraints.NotBlank

data class DelegateHeadRequest(
    @field:NotBlank
    val uuid: UUID
)