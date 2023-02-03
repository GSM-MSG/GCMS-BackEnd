package com.msg.gcms.domain.user.presentaion.data.dto

import com.msg.gcms.domain.club.enums.ClubType

data class SearchRequirementDto(
    val clubType: ClubType,
    val name: String
)
