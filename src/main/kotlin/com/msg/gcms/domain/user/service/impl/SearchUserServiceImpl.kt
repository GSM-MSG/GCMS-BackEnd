package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.domain.user.utils.UserValidator
import com.msg.gcms.global.annotation.ServiceWithReadOnlyTransaction

@ServiceWithReadOnlyTransaction
class SearchUserServiceImpl(
    val userValidator: UserValidator,
    val userRepository: UserRepository,
    val userConverter: UserConverter,
) : SearchUserService {
    override fun execute(dto: SearchRequirementDto): List<SearchUserDto> =
        if (dto.clubType == ClubType.EDITORIAL) {
            userRepository.findByNicknameContaining(dto.name)
                .let { userValidator.validateUser(it, dto.clubType) }
                .map { userConverter.toDto(it) }
        } else {
            userRepository.findUserNotJoin(dto.clubType, dto.name)
                .let { userValidator.validateUser(it, dto.clubType) }
                .map { userConverter.toDto(it) }
        }
}