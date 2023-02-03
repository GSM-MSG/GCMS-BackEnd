package com.msg.gcms.domain.user.presentaion.data.response

import com.msg.gcms.domain.club.domain.entity.enums.ClubType
import java.util.*

data class UserResponseDto(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?,
    val clubs: List<ClubResponseDto>
) {
  data class ClubResponseDto(
      val id: Long,
      val type: ClubType,
      val name: String,
      val bannerImg: String
  )
}
