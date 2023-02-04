package com.msg.gcms.testUtils

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListDto
import com.msg.gcms.domain.user.domain.entity.User
import kotlin.random.Random

object ClubMemberDataUtil {
    fun entity(club: Club, user: User) = ClubMember(
        id = Random.nextLong(),
        club = club,
        user = user
    )

    fun clubMemberDto(user: User, scope: MemberScope) = ClubMemberListDto.SingleClubMemberDto(
        uuid = user.id,
        email = user.email,
        name = user.nickname,
        grade = user.grade,
        classNum = user.classNum,
        number = user.number,
        profileImg = user.profileImg,
        scope = scope
    )
}