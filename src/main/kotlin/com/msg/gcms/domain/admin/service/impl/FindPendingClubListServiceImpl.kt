package com.msg.gcms.domain.admin.service.impl

import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto
import com.msg.gcms.domain.admin.service.FindPendingClubListService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindPendingClubListServiceImpl : FindPendingClubListService{
    override fun execute(): List<PendingClubDto> {
        TODO("Not yet implemented")
    }
}