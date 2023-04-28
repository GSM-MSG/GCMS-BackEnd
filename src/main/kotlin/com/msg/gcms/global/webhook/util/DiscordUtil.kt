package com.msg.gcms.global.webhook.util

import com.msg.gcms.domain.club.enums.ClubType

interface DiscordUtil{
    fun createClubMessage(clubName: String, clubType: ClubType, clubBannerImg: String): String
    fun sendDiscordMessage(message: String)
}