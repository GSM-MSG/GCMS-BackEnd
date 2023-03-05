package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto
import com.msg.gcms.domain.admin.service.FindPendingClubListService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindPendingClubListServiceImpl(
    private val clubRepository: ClubRepository,
    private val adminConverter: AdminConverter
) : FindPendingClubListService{
    override fun execute(): List<PendingClubDto> =
        clubRepository.findAllByClubStatus(ClubStatus.PENDING)
            .map { adminConverter.toDto(it) }
}