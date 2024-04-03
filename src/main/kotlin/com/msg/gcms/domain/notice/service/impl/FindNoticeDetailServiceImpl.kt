package com.msg.gcms.domain.notice.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.notice.domain.repository.NoticeRepository
import com.msg.gcms.domain.notice.exception.NoticeNotFoundException
import com.msg.gcms.domain.notice.presentation.data.response.FindNoticeDetailResponseDto
import com.msg.gcms.domain.notice.service.FindNoticeDetailService
import com.msg.gcms.domain.notice.utils.NoticeConverter
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithReadOnlyTransaction
class FindNoticeDetailServiceImpl(
    private val userUtil: UserUtil,
    private val noticeRepository: NoticeRepository,
    private val noticeConverter: NoticeConverter
) : FindNoticeDetailService {
    override fun execute(id: Long): FindNoticeDetailResponseDto {
        val currentUser = userUtil.fetchCurrentUser()

        val notice = noticeRepository.findByIdOrNull(id) ?: throw NoticeNotFoundException()

        if (notice.club.user != currentUser && currentUser.roles[0] != Role.ROLE_ADMIN)
            throw HeadNotSameException()

        return noticeConverter.toResponse(notice)
    }
}