package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.service.FindClubListService
import com.msg.gcms.domain.club.utils.ClubConverter
import org.springframework.stereotype.Service

@Service
class FindClubListServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubConverter: ClubConverter
): FindClubListService {
    override fun execute(clubTypeDto: ClubTypeDto): List<ClubListDto> =
        clubRepository.findByType(clubTypeDto.clubType)
            .map { clubConverter.toDto(it) }
}