package com.msg.gcms.domain.user.presentaion.data.dto

import com.msg.gcms.domain.club.domain.entity.enums.ClubType
import com.msg.gcms.domain.club.enums.ClubType
import java.util.*

data class UserDto (
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?,
    val clubs: List<ClubDto>
) {
    data class  ClubDto(
        val id: Long,
        val type: ClubType,
        val name: String,
        val bannerImg: String
    )
}