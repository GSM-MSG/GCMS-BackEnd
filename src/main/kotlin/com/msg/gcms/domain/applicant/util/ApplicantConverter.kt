package com.msg.gcms.domain.applicant.util

import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.presentation.data.dto.ApplicantListDto
import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import com.msg.gcms.domain.applicant.presentation.data.response.ApplicantListResponseDto
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.Scope
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User

interface ApplicantConverter {
    fun toDto(acceptRequestDto: AcceptRequestDto): AcceptDto
    fun toDto(user: User): ApplicantListDto.ApplicantDto
    fun toDto(scope: Scope, dto: List<ApplicantListDto.ApplicantDto>): ApplicantListDto
    fun toResponseDto(dto: ApplicantListDto.ApplicantDto): ApplicantListResponseDto.ApplicantResponseDto
    fun toResponseDto(
        dto: ApplicantListDto,
        responseDto: List<ApplicantListResponseDto.ApplicantResponseDto>
    ): ApplicantListResponseDto
    fun toEntity(club: Club, user: User): ClubMember
}