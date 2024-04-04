package com.msg.gcms.domain.notice.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.domain.notice.domain.repository.NoticeRepository
import com.msg.gcms.domain.notice.presentation.data.response.NoticeListDto
import com.msg.gcms.domain.notice.service.NoticeListService
import com.msg.gcms.domain.notice.utils.NoticeConverter
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull

@ServiceWithReadOnlyTransaction
class NoticeListServiceImpl(
        private val noticeRepository: NoticeRepository,
        private val clubRepository: ClubRepository,
        private val noticeConverter: NoticeConverter,
        private val userUtil: UserUtil
) : NoticeListService {
    override fun execute(clubId: Long): NoticeListDto {
        val user = userUtil.fetchCurrentUser()
        val club = clubRepository.findByIdOrNull(clubId)
                ?: throw ClubNotFoundException()

        if (user.roles[0] == Role.ROLE_STUDENT) {
            if (!user.club.contains(club)) {
                throw NotClubMemberException()
            }
        }

        val notices = noticeRepository.findByClub(club)
                .map { noticeConverter.toDto(it) }

        return noticeConverter.toListDto(notices)
    }
}
