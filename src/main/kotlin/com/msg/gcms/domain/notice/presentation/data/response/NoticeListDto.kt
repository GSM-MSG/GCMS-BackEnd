package com.msg.gcms.domain.notice.presentation.data.response

import java.time.LocalDateTime

data class NoticeListDto(
    val notices: List<NoticeResponseDto>
) {
    data class NoticeResponseDto(
            val id: Long,
            val title: String,
            val username: String,
            val createdAt: LocalDateTime
    )
}
