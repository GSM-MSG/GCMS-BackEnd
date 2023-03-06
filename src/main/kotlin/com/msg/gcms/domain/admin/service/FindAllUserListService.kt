package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.admin.presentation.data.dto.FindAllUserListDto

interface FindAllUserListService {
    fun execute() : List<FindAllUserListDto>
}