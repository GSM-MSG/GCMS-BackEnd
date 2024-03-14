package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserIsHeadException
import com.msg.gcms.domain.user.service.WithdrawUserService
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil


@ServiceWithTransaction
class WithdrawUserServiceImpl(
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository
) : WithdrawUserService {
    override fun execute() {
        val user = userUtil.fetchCurrentUser()
        if(clubRepository.existsByUser(user)) {
            throw UserIsHeadException()
        }
        userRepository.delete(user)
    }
}