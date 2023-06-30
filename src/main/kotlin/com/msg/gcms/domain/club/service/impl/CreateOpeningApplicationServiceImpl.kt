package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.OpeningApplication
import com.msg.gcms.domain.club.domain.repository.OpeningApplicationRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.OpeningApplicationAlreadyExistException
import com.msg.gcms.domain.club.presentation.data.dto.OpeningApplicationDto
import com.msg.gcms.domain.club.service.CreateOpeningApplicationService
import com.msg.gcms.domain.club.utils.OpeningApplicationConverter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateOpeningApplicationServiceImpl(
    private val clubRepository: ClubRepository,
    private val openingApplicationRepository: OpeningApplicationRepository,
    private val openingApplicationConverter: OpeningApplicationConverter
) : CreateOpeningApplicationService {
    override fun execute(clubId: Long, openingApplicationDto: OpeningApplicationDto) {
        val club = clubRepository.findByIdOrNull(clubId) ?: throw ClubNotFoundException()

        if (club.openingApplication != null) {
            throw OpeningApplicationAlreadyExistException()
        }

        val openingApplication = OpeningApplication(
            subject = openingApplicationDto.subject,
            reason = openingApplicationDto.reason,
            target = openingApplicationDto.target,
            effect = openingApplicationDto.effect
        )

        openingApplicationRepository.save(openingApplication)

        clubRepository.save(openingApplicationConverter.toEntity(openingApplication, club))
    }
}