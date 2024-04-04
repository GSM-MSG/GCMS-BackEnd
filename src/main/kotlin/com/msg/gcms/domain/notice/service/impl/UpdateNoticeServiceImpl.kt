package com.msg.gcms.domain.notice.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.notice.domain.entity.Notice
import com.msg.gcms.domain.notice.domain.repository.NoticeRepository
import com.msg.gcms.domain.notice.exception.NoticeNotFoundException
import com.msg.gcms.domain.notice.presentation.data.dto.UpdateNoticeDto
import com.msg.gcms.domain.notice.service.UpdateNoticeService
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class UpdateNoticeServiceImpl(
    private val userUtil: UserUtil,
    private val noticeRepository: NoticeRepository
) : UpdateNoticeService {
    override fun execute(id: Long, dto: UpdateNoticeDto) {
        val user = userUtil.fetchCurrentUser()

        val notice = noticeRepository.findByIdOrNull(id)
            ?: throw NoticeNotFoundException()

        if(user.roles[0] == Role.ROLE_STUDENT) {
            if (user != notice.club.user) throw HeadNotSameException()
        }

        val updatedNotice = Notice(
            id = notice.id,
            title = dto.title,
            content = notice.content,
            club = notice.club,
            user = notice.user,
            createdAt = notice.createdAt,
        )

        noticeRepository.save(updatedNotice)
    }
}