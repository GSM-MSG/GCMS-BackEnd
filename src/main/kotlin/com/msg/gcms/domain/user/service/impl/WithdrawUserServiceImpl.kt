package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserIsHeadException
import com.msg.gcms.domain.user.service.WithdrawUserService
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawUserServiceImpl(
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository
) : WithdrawUserService {
    @Transactional(rollbackFor = [Exception::class])
    override fun execute() {
        val user = userUtil.fetchCurrentUser()
        if(clubRepository.existsByUser(user)) {
            throw UserIsHeadException()
        }
        userRepository.delete(user)
    }
}