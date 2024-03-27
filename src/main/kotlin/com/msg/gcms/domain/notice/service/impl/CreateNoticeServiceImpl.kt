package com.msg.gcms.domain.notice.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.notice.domain.repository.NoticeRepository
import com.msg.gcms.domain.notice.presentation.data.dto.NoticeDto
import com.msg.gcms.domain.notice.service.CreateNoticeService
import com.msg.gcms.domain.notice.utils.NoticeConverter
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithTransaction
class CreateNoticeServiceImpl(
    private val userUtil: UserUtil,
    private val clubRepository: ClubRepository,
    private val noticeConverter: NoticeConverter,
    private val noticeRepository: NoticeRepository
) : CreateNoticeService {
    override fun execute(clubId: Long, dto: NoticeDto) {
        val user = userUtil.fetchCurrentUser()

        val club = clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException()

        when(user.roles) {
            listOf(Role.ROLE_ADMIN) -> {}
            listOf(Role.ROLE_STUDENT) -> {
                if (user != club.user)
                    throw HeadNotSameException()
            }
        }

        noticeConverter.toEntity(dto, user, club)
            .let { noticeRepository.save(it) }
    }
}