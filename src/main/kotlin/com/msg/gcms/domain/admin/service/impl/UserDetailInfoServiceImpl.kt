package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.ClubInfoDto
import com.msg.gcms.domain.admin.presentation.data.dto.UserDetailInfoDto
import com.msg.gcms.domain.admin.presentation.data.request.UserDetailInfoRequest
import com.msg.gcms.domain.admin.service.UserDetailInfoService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailInfoServiceImpl(
        private val userRepository: UserRepository,
        private val clubRepository: ClubRepository,
        private val adminConverter: AdminConverter
) : UserDetailInfoService {
    override fun execute(userDetailInfoRequest: UserDetailInfoRequest): UserDetailInfoDto {
        val user = userRepository.findByIdOrNull(UUID.fromString(userDetailInfoRequest.uuid))
                ?: throw UserNotFoundException()
        val clubList = clubRepository.findByUserAndClubStatus(user, ClubStatus.CREATED)
                .map { ClubInfoDto(
                        id = it.id,
                        bannerImg = it.bannerImg,
                        name = it.name,
                        type = it.type
                ) }

        return adminConverter.toDto(user, clubList)
    }
}