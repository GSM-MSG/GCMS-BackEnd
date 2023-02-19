package com.msg.gcms.domain.applicant.util

<<<<<<< HEAD
import com.msg.gcms.domain.applicant.presentation.data.dto.AcceptDto
import com.msg.gcms.domain.applicant.presentation.data.request.AcceptRequestDto
import org.springframework.stereotype.Component

@Component
class ApplicantConverter {
    fun toDto(acceptRequestDto: AcceptRequestDto): AcceptDto =
        AcceptDto(
            uuid = acceptRequestDto.uuid
        )
=======
import com.msg.gcms.domain.applicant.presentation.data.dto.ApplicantListDto
import com.msg.gcms.domain.applicant.presentation.data.response.ApplicantListResponseDto
import com.msg.gcms.domain.club.enums.Scope
import com.msg.gcms.domain.user.domain.entity.User

interface ApplicantConverter {
    fun toDto(user: User): ApplicantListDto.ApplicantDto
    fun toDto(scope: Scope, dto: List<ApplicantListDto.ApplicantDto>): ApplicantListDto
    fun toResponseDto(dto: ApplicantListDto.ApplicantDto): ApplicantListResponseDto.ApplicantResponseDto
    fun toResponseDto(dto: ApplicantListDto, responseDto: List<ApplicantListResponseDto.ApplicantResponseDto>): ApplicantListResponseDto
>>>>>>> b67230face0437b613ec4e4908b3e2220b2483d2
}