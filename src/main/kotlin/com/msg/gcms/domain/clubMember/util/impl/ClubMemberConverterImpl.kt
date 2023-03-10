package com.msg.gcms.domain.clubMember.util.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberExitDto
import com.msg.gcms.domain.clubMember.presentation.data.request.ExitClubMemberRequest
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto
import com.msg.gcms.domain.clubMember.presentation.data.request.DelegateHeadRequest
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListDto
import com.msg.gcms.domain.clubMember.presentation.data.response.ClubMemberListResponse
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import org.springframework.stereotype.Component

@Component
class ClubMemberConverterImpl : ClubMemberConverter {
    override fun toDto(entity: ClubMember, scope: MemberScope): ClubMemberListDto.SingleClubMemberDto =
        ClubMemberListDto.SingleClubMemberDto(
            uuid = entity.user.id,
            email = entity.user.email,
            name = entity.user.nickname,
            grade = entity.user.grade,
            classNum = entity.user.classNum,
            number = entity.user.number,
            profileImg = entity.user.profileImg,
            scope = scope
        )

    override fun toDto(entity: Club): ClubMemberListDto.SingleClubMemberDto =
        ClubMemberListDto.SingleClubMemberDto(
            uuid = entity.user.id,
            email = entity.user.email,
            name = entity.user.nickname,
            grade = entity.user.grade,
            classNum = entity.user.classNum,
            number = entity.user.number,
            profileImg = entity.user.profileImg,
            scope = MemberScope.HEAD
        )

    override fun toDto(delegateHeadRequest: DelegateHeadRequest): DelegateHeadDto =
        DelegateHeadDto(delegateHeadRequest.uuid)

    override fun toListDto(scope: MemberScope, dto: List<ClubMemberListDto.SingleClubMemberDto>): ClubMemberListDto =
        ClubMemberListDto(
            scope = scope,
            clubMember = dto
        )

    override fun toResponse(dto: ClubMemberListDto): ClubMemberListResponse =
        ClubMemberListResponse(
            scope = dto.scope,
            clubMember = dto.clubMember
        )

    override fun toDto(clubId: Long, requestDto: ExitClubMemberRequest): ClubMemberExitDto =
        ClubMemberExitDto(
            clubId = clubId,
            uuid = requestDto.uuid
        )
}