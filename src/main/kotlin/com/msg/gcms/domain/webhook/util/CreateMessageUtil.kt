package com.msg.gcms.domain.webhook.util

import com.msg.gcms.domain.club.enums.ClubType

interface CreateMessageUtil {
    fun execute(clubName: String, clubType: ClubType, clubImg: String): String
}