package com.msg.gcms.domain.notice.service

import com.msg.gcms.domain.notice.presentation.data.response.NoticeListDto

interface NoticeListService {
    fun execute(clubId: Long): NoticeListDto
}