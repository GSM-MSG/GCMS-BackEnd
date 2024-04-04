package com.msg.gcms.domain.notice.presentation.data.response

import java.time.LocalDateTime

data class FindNoticeDetailResponseDto(
    val title: String,
    val content : String,
    val username : String,
    val userProfileImg : String?,
    val createdAt : LocalDateTime
)