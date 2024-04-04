package com.msg.gcms.domain.notice.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.notice.domain.entity.Notice
import com.msg.gcms.domain.notice.presentation.data.dto.NoticeDto
import com.msg.gcms.domain.notice.presentation.data.dto.UpdateNoticeDto
import com.msg.gcms.domain.notice.presentation.data.request.CreateNoticeRequestDto
import com.msg.gcms.domain.notice.presentation.data.request.UpdateNoticeRequestDto
import com.msg.gcms.domain.notice.presentation.data.response.FindNoticeDetailResponseDto
import com.msg.gcms.domain.notice.presentation.data.response.NoticeListDto
import com.msg.gcms.domain.notice.presentation.data.response.NoticeListResponseDto
import com.msg.gcms.domain.notice.utils.NoticeConverter
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class NoticeConverterImpl : NoticeConverter {
    override fun toDto(requestDto: CreateNoticeRequestDto): NoticeDto = NoticeDto(
        title = requestDto.title,
        content = requestDto.content
    )

    override fun toDto(entity: Notice): NoticeListDto.NoticeResponseDto = NoticeListDto.NoticeResponseDto(
        id = entity.id,
        title = entity.title,
        username = entity.user.nickname,
        createdAt = entity.createdAt
    )

    override fun toDto(requestDto: UpdateNoticeRequestDto): UpdateNoticeDto =
        UpdateNoticeDto(
            title = requestDto.title,
            content = requestDto.content
        )

    override fun toEntity(dto: NoticeDto, user: User, club: Club): Notice = Notice(
        id = dto.id,
        title = dto.title,
        content = dto.content,
        createdAt = dto.createdAt,
        user = user,
        club = club
    )


    override fun toListDto(dto: List<NoticeListDto.NoticeResponseDto>): NoticeListDto =
         NoticeListDto(
            notices = dto
    )

    override fun toResponse(dto: NoticeListDto): NoticeListResponseDto =
            NoticeListResponseDto(
                    notices = dto.notices
            )

    override fun toResponse(notice: Notice): FindNoticeDetailResponseDto = FindNoticeDetailResponseDto(
        title = notice.title,
        content = notice.content,
        username = notice.user.nickname,
        userProfileImg = notice.user.profileImg,
        createdAt = notice.createdAt
    )

}