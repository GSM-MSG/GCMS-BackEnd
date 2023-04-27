package com.msg.gcms.domain.webhook.util

import com.msg.gcms.domain.club.enums.ClubType

interface DiscordUtil{
    fun createClubApplyMessage(clubName: String, clubType: ClubType, clubBannerImg: String): String
    fun sendDiscordMessage(message: String)
}