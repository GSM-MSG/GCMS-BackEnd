package com.msg.gcms.domain.applicant.util.impl

import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.presentation.data.dto.ApplicantListDto
import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import com.msg.gcms.domain.applicant.presentation.data.response.ApplicantListResponseDto
import com.msg.gcms.domain.applicant.util.ApplicantConverter
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.Scope
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class ApplicantConverterImpl : ApplicantConverter {
    override fun toDto(acceptRequestDto: AcceptRequestDto): AcceptDto =
        AcceptDto(
            uuid = acceptRequestDto.uuid
        )

    override fun toDto(user: User): ApplicantListDto.ApplicantDto = ApplicantListDto.ApplicantDto(
        uuid = user.id,
        email = user.email,
        name = user.nickname,
        grade = user.grade,
        classNum = user.classNum,
        number = user.number,
        profileImg = user.profileImg
    )

    override fun toDto(scope: Scope, dto: List<ApplicantListDto.ApplicantDto>): ApplicantListDto = ApplicantListDto(
        scope = scope,
        applicantList = dto
    )

    override fun toResponseDto(dto: ApplicantListDto.ApplicantDto): ApplicantListResponseDto.ApplicantResponseDto =
        ApplicantListResponseDto.ApplicantResponseDto(
            uuid = dto.uuid,
            email = dto.email,
            name = dto.name,
            grade = dto.grade,
            classNum = dto.classNum,
            number = dto.number,
            profileImg = dto.profileImg
        )

    override fun toResponseDto(
        dto: ApplicantListDto,
        responseDto: List<ApplicantListResponseDto.ApplicantResponseDto>,
    ): ApplicantListResponseDto = ApplicantListResponseDto(
        scope = dto.scope,
        applicantList = responseDto
    )

    override fun toEntity(club: Club, user: User): ClubMember =
        ClubMember(
            club = club,
            user = user
        )
}