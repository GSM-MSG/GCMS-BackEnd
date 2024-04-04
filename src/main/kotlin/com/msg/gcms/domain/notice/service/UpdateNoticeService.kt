package com.msg.gcms.domain.notice.service

import com.msg.gcms.domain.notice.presentation.data.dto.UpdateNoticeDto

interface UpdateNoticeService {
    fun execute(id: Long, dto: UpdateNoticeDto)
}