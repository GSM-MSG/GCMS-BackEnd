package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.entity.ClubOpeningApplication

interface ClubOpeningApplicationServiceConverter {
    fun toEntity(clubOpeningApplication: ClubOpeningApplication, club: Club): Club
}