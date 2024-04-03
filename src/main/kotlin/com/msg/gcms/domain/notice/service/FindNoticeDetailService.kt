package com.msg.gcms.domain.notice.service

import com.msg.gcms.domain.notice.presentation.data.response.FindNoticeDetailResponseDto

interface FindNoticeDetailService {
    fun execute(id: Long): FindNoticeDetailResponseDto
}