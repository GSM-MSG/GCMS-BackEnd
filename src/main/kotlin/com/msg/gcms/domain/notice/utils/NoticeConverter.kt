package com.msg.gcms.domain.notice.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.notice.domain.entity.Notice
import com.msg.gcms.domain.notice.presentation.data.dto.NoticeDto
import com.msg.gcms.domain.notice.presentation.data.request.CreateNoticeRequestDto
import com.msg.gcms.domain.notice.presentation.data.response.FindNoticeDetailResponseDto
import com.msg.gcms.domain.notice.presentation.data.response.NoticeListDto
import com.msg.gcms.domain.notice.presentation.data.response.NoticeListResponseDto
import com.msg.gcms.domain.user.domain.entity.User

interface NoticeConverter {
    fun toDto(requestDto: CreateNoticeRequestDto): NoticeDto
    fun toResponse(notice: Notice): FindNoticeDetailResponseDto
    fun toEntity(dto: NoticeDto, user: User, club: Club): Notice

    fun toDto(entity: Notice): NoticeListDto.NoticeResponseDto

    fun toListDto(dto: List<NoticeListDto.NoticeResponseDto>): NoticeListDto

    fun toResponse(dto: NoticeListDto): NoticeListResponseDto
}