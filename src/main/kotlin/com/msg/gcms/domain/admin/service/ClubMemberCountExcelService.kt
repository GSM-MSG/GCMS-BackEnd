package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.club.enums.ClubType

interface ClubMemberCountExcelService {
    fun execute(clubType: ClubType): ByteArray
}