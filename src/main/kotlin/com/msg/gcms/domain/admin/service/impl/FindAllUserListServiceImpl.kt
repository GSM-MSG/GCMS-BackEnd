package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.FindAllUserListDto
import com.msg.gcms.domain.admin.service.FindAllUserListService
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class FindAllUserListServiceImpl(
        private val userRepository: UserRepository,
        private val adminConverter: AdminConverter
) : FindAllUserListService {
    override fun execute(): List<FindAllUserListDto> =
        userRepository.findAll()
                .filter { it.roles[0] == Role.ROLE_STUDENT }
                .map { adminConverter.toDto(it) }
}