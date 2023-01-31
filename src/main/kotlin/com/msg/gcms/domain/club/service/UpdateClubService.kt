package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubDto

interface UpdateClubService {
    fun execute(id: Long, clubDto: ClubDto)
}