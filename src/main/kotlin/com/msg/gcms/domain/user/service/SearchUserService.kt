package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto

interface SearchUserService {
    fun execute(dto: SearchRequirementDto): List<SearchUserDto>
}