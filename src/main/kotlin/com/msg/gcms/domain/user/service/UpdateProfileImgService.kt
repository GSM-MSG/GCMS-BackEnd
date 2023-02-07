package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.user.presentaion.data.dto.ProfileImgDto

interface UpdateProfileImgService {
    fun execute(dto: ProfileImgDto)
}