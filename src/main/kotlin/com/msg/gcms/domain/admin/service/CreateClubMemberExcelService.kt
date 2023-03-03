package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.club.enums.ClubType

interface CreateClubMemberExcelService {
    fun execute(clubType: ClubType): ByteArray
}