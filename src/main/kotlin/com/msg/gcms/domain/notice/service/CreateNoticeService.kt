package com.msg.gcms.domain.notice.service

import com.msg.gcms.domain.notice.presentation.data.dto.NoticeDto

interface CreateNoticeService {
    fun execute(clubId: Long, dto: NoticeDto)
}