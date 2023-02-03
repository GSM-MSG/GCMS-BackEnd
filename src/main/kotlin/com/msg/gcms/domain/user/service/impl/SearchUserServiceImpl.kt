package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.utils.UserConverter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchUserServiceImpl(
    val userRepository: UserRepository,
    val clubRepository: ClubRepository,
    val userConverter: UserConverter,
) : SearchUserService {
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun execute(dto: SearchRequirementDto): List<SearchUserDto> =
        if (dto.clubType.name != "MAJOR"
            && dto.clubType.name != "FREEDOM") {
            userRepository.findByNicknameContaining(dto.name)
                .map { userConverter.toDto(it) }
        } else {
            userRepository.findUserNotJoin(dto.clubType, dto.name)
                .filter { !verifyUserIsHead(it) }
                .map { userConverter.toDto(it) }
        }
    private fun verifyUserIsHead(user: User): Boolean =
        clubRepository.existsByUser(user)
}