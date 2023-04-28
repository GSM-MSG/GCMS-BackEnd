package com.msg.gcms.domain.webhook.util

import com.msg.gcms.domain.club.enums.ClubType

interface DiscordUtil{
    fun createClubMessage(clubName: String, clubType: ClubType, clubBannerImg: String)
    fun sendDiscordMessage(message: String)
}