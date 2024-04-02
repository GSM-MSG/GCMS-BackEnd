package com.msg.gcms.domain.notice.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.notice.domain.repository.NoticeRepository
import com.msg.gcms.domain.notice.exception.NoticeNotFoundException
import com.msg.gcms.domain.notice.service.DeleteNoticeService
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class DeleteNoticeServiceImpl(
        private val userUtil: UserUtil,
        private val noticeRepository: NoticeRepository
): DeleteNoticeService {
    override fun execute(id: Long) {
        val user = userUtil.fetchCurrentUser()
        val notice = noticeRepository.findByIdOrNull(id)
                ?: throw NoticeNotFoundException()

        if (user.roles.contains(Role.ROLE_STUDENT)) {
            if(notice.club.user != user) throw HeadNotSameException()
        }

        noticeRepository.deleteById(id)
    }
}