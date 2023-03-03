package com.msg.gcms.domain.admin.service

import com.msg.gcms.domain.club.enums.ClubType

interface CreateClubMemberExcelByClassNumService {
    fun execute(clubType: ClubType): ByteArray
}