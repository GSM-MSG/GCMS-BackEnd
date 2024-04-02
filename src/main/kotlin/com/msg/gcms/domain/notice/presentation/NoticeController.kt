package com.msg.gcms.domain.notice.presentation

import com.msg.gcms.domain.notice.presentation.data.request.CreateNoticeRequestDto
import com.msg.gcms.domain.notice.service.CreateNoticeService
import com.msg.gcms.domain.notice.service.DeleteNoticeService
import com.msg.gcms.domain.notice.utils.NoticeConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@RequestController("/notification")
class NoticeController(
    private val createNoticeService: CreateNoticeService,
    private val noticeConverter: NoticeConverter,
    private val deleteNoticeService: DeleteNoticeService
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
            @PathVariable("id") id: Long
    ): ResponseEntity<Unit> =
        deleteNoticeService.execute(id)
             .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

}