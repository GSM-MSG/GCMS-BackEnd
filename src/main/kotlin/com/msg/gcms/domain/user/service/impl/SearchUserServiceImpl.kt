package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.domain.user.utils.UserValidator
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchUserServiceImpl(
    val userValidator: UserValidator,
    val userRepository: UserRepository,
    val userConverter: UserConverter,
) : SearchUserService {
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun execute(dto: SearchRequirementDto): List<SearchUserDto> {
        return if (dto.clubType == ClubType.EDITORIAL
        ) {
            userRepository.findByNicknameContaining(dto.name)
                .let { userValidator.validateUser(it, dto.clubType) }
                .map { userConverter.toDto(it) }
        } else {
            userRepository.findUserNotJoin(dto.clubType, dto.name)
                .let { userValidator.validateUser(it, dto.clubType) }
                .map { userConverter.toDto(it) }
        }
    }
}