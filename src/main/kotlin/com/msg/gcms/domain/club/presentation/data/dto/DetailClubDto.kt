package com.msg.gcms.domain.club.presentation.data.dto

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.enums.Scope
import java.util.*

data class DetailClubDto(
    val id: Long,
    val type: ClubType,
    val bannerImg: String,
    val name: String,
    val content: String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val isOpened: Boolean,
    val activityImgs: List<String>,
    val head: UserDto,
    val member: List<UserDto>,
    val scope: Scope,
    val isApplied: Boolean
) {
    data class UserDto(
        val uuid: UUID,
        val email: String,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val profileImg: String?
    )
}
