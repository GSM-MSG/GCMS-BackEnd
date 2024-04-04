package com.msg.gcms.domain.notice.presentation

import com.msg.gcms.domain.notice.presentation.data.request.CreateNoticeRequestDto
import com.msg.gcms.domain.notice.presentation.data.request.UpdateNoticeRequestDto
import com.msg.gcms.domain.notice.presentation.data.response.FindNoticeDetailResponseDto
import com.msg.gcms.domain.notice.presentation.data.response.NoticeListResponseDto
import com.msg.gcms.domain.notice.service.*
import com.msg.gcms.domain.notice.utils.NoticeConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestController("/notification")
class NoticeController(
    private val createNoticeService: CreateNoticeService,
    private val noticeConverter: NoticeConverter,
    private val deleteNoticeService: DeleteNoticeService,
    private val findNoticeDetailService: FindNoticeDetailService,
    private val noticeListService: NoticeListService,
    private val updateNoticeService: UpdateNoticeService
) {
    @PostMapping("/{club_id}")
    fun createNotice(
        @PathVariable("club_id") clubId: Long,
        @RequestBody @Valid requestDto: CreateNoticeRequestDto
    ): ResponseEntity<Unit> =
        noticeConverter.toDto(requestDto)
            .let { createNoticeService.execute(clubId, it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @DeleteMapping("/{id}")
    fun deleteNotice(
            @PathVariable id: Long
    ): ResponseEntity<Unit> =
        deleteNoticeService.execute(id)
             .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @GetMapping("/{id}")
    fun findNoticeDetail(
        @PathVariable id: Long
    ): ResponseEntity<FindNoticeDetailResponseDto> =
        findNoticeDetailService.execute(id)
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }

    @GetMapping("/{club_id}/all")
    fun noticeList(
            @PathVariable("club_id") clubId: Long
    ): ResponseEntity<NoticeListResponseDto> =
        noticeListService.execute(clubId)
            .let { ResponseEntity.ok().body(noticeConverter.toResponse(it)) }

    @PatchMapping("/{id}")
    fun updateNotice(
        @PathVariable id: Long,
        @RequestBody @Valid updateNoticeRequestDto: UpdateNoticeRequestDto
    ) : ResponseEntity<Unit> =
        noticeConverter.toDto(updateNoticeRequestDto)
            .let { updateNoticeService.execute(id, it) }
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
}