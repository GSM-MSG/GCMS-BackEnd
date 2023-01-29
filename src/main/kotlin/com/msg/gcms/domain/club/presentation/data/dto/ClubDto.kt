package com.msg.gcms.domain.club.presentation.data.dto

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import java.util.UUID

data class ClubDto(
    val id: Long,
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
    fun toEntity(user: User): Club =
        Club(
            id = id,
            type = type,
            name = name,
            content = content,
            bannerImg = bannerImg,
            contact = contact,
            notionLink = notionLink,
            teacher = teacher,
            activityImg = mutableListOf(),
            clubMember = mutableListOf(),
            user = user,
            applicant = listOf(),
            isOpened = true,
        )
}