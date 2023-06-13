package com.msg.gcms.domain.admin.service

interface ClubOperationPlanHwpService {
    fun execute(clubId: Long): ByteArray
}