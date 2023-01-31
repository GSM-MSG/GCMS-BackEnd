package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.ClubDto

interface CreateClubService {
    fun execute(clubDto: ClubDto)
}