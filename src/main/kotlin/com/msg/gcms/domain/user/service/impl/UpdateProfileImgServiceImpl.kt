package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.ProfileImgDto
import com.msg.gcms.domain.user.service.UpdateProfileImgService
import com.msg.gcms.global.annotation.ServiceWithTransaction
import com.msg.gcms.global.util.UserUtil

@ServiceWithTransaction
class UpdateProfileImgServiceImpl(
    private val userUtil: UserUtil,
    private val userRepository: UserRepository
) : UpdateProfileImgService {
    override fun execute(dto: ProfileImgDto) {
        val user = userUtil.fetchCurrentUser()
        user.updateProfileImg(dto.profileImg)
        userRepository.save(user)
    }
}