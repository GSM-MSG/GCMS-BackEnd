package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.ProfileImgDto
import com.msg.gcms.domain.user.service.UpdateProfileImgService
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateProfileImgServiceImpl(
    private val userUtil: UserUtil,
    private val userRepository: UserRepository
) : UpdateProfileImgService {
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun execute(dto: ProfileImgDto) {
        val user = userUtil.fetchCurrentUser()
        user.updateProfileImg(dto.profileImg)
        userRepository.save(user)
    }
}