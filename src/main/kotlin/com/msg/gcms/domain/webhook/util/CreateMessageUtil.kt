package com.msg.gcms.domain.webhook.util

interface CreateMessageUtil {
    fun execute(club_name: String, club_type: String, club_img: String): String
}