package com.msg.gcms.domain.clubMember.service

import com.msg.gcms.domain.clubMember.presentation.data.dto.ChangeHeadDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto

interface DelegateHeadService {
    fun execute(clubId: Long, delegateHeadDto: DelegateHeadDto): ChangeHeadDto
}