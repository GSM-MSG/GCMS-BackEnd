package com.msg.gcms.domain.user.service.impl

import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.presentaion.data.dto.SearchUserDto
import com.msg.gcms.domain.user.service.SearchUserService
import org.springframework.stereotype.Service

@Service
class SearchUserServiceImpl : SearchUserService {
    override fun execute(dto: SearchRequirementDto): List<SearchUserDto> {
        TODO("Not yet implemented")
    }
}