package com.msg.gcms.domain.club.presentation.data.request

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import java.util.*

data class CreateClubRequest(
    val type: ClubType,
    val name: String,
    val content: String,
    val bannerImg: String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val activityImgs: List<String>,
    val member: List<UUID>,
) {
   fun toDto(): ClubDto =
       ClubDto(
           id = 0,
           type = type,
           name = name,
           content = content,
           bannerImg = bannerImg,
           contact = contact,
           notionLink = notionLink,
           teacher = teacher,
           activityImgs = activityImgs,
           member = member
       )
}