package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.ClubOpeningApplication
import com.msg.gcms.domain.club.domain.repository.ClubOpeningApplicationRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.presentation.data.dto.ClubOpeningApplicationDto
import com.msg.gcms.domain.club.service.CreateClubOpeningApplicationService
import com.msg.gcms.domain.club.utils.ClubOpeningApplicationServiceConverter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateClubOpeningApplicationServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubOpeningApplicationRepository: ClubOpeningApplicationRepository,
    private val clubOpeningApplicationServiceConverter: ClubOpeningApplicationServiceConverter
) : CreateClubOpeningApplicationService {
    override fun execute(clubId: Long, clubOpeningApplicationDto: ClubOpeningApplicationDto) {
        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()

        val clubOpeningApplication = ClubOpeningApplication(
            subject = clubOpeningApplicationDto.subject,
            reason = clubOpeningApplicationDto.reason,
            target = clubOpeningApplicationDto.target,
            effect = clubOpeningApplicationDto.effect
        )

        clubOpeningApplicationRepository.save(clubOpeningApplication)

        clubRepository.save(clubOpeningApplicationServiceConverter.toEntity(clubOpeningApplication, club))
    }
}