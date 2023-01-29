package com.msg.gcms.domain.clubMember.util.impl

import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope
import org.springframework.stereotype.Component

@Component
class ClubMemberConverterImpl : ClubMemberConverter {
    override fun toDto(entity: ClubMember): ClubMemberDto = ClubMemberDto(
        uuid = entity.user.id,
        email = entity.user.email,
        name = entity.user.nickname,
        grade = entity.user.grade,
        classNum = entity.user.classNum,
        number = entity.user.number,
        profileImg = entity.user.profileImg,
        scope = entity.scope
    )

    override fun toListDto(scope: MemberScope, dto: List<ClubMemberDto>): ClubMemberListDto = ClubMemberListDto(
        scope = scope,
        clubMember = dto
    )

    override fun toResponse(dto: ClubMemberListDto): ClubMemberListResponse = ClubMemberListResponse(
        scope = dto.scope,
        clubMember = dto.clubMember
    )
}