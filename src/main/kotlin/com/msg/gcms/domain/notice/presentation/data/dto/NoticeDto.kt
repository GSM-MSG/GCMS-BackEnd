package com.msg.gcms.domain.notice.presentation.data.dto

import java.time.LocalDateTime

data class NoticeDto(
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)