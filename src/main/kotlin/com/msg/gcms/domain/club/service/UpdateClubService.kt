package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.dto.UpdateClubDto

interface UpdateClubService {
    fun execute(id: Long, clubDto: UpdateClubDto)
}