package com.msg.gcms.domain.webhook.util

import com.msg.gcms.domain.club.enums.ClubType

interface CreateMessageUtil {
    fun execute(club_name: String, club_type: ClubType, club_img: String): String
}