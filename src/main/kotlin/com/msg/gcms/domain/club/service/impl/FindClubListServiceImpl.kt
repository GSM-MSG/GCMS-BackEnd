package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction
import org.springframework.transaction.annotation.Transactional

@ServiceWithReadOnlyTransaction
class FindClubListServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubConverter: ClubConverter
) : FindClubListService {
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun execute(clubTypeDto: ClubTypeDto): List<ClubListDto> =
        clubRepository.findByTypeAndClubStatus(clubTypeDto.clubType, ClubStatus.CREATED)
            .map { clubConverter.toDto(it) }
}