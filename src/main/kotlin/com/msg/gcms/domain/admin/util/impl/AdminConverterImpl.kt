package com.msg.gcms.domain.admin.util.impl

import com.msg.gcms.domain.admin.presentation.data.dto.ClubInfoDto
import com.msg.gcms.domain.admin.presentation.data.dto.FindAllUserListDto
import com.msg.gcms.domain.admin.presentation.data.dto.PendingClubDto
import com.msg.gcms.domain.admin.presentation.data.dto.UserDetailInfoDto
import com.msg.gcms.domain.admin.presentation.data.response.FindAllUserListResponse
import com.msg.gcms.domain.admin.presentation.data.response.PendingClubResponse
import com.msg.gcms.domain.admin.presentation.data.response.UserDetailInfoResponse
import com.msg.gcms.domain.admin.util.AdminConverter
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class AdminConverterImpl : AdminConverter {
    override fun toDto(club: Club): PendingClubDto =
        PendingClubDto(
            id = club.id,
            bannerImg = club.bannerImg,
            name = club.name,
            type = club.type,
            content = club.content
        )

    override fun toResponse(dto: PendingClubDto): PendingClubResponse =
        PendingClubResponse(
            id = dto.id,
            bannerImg = dto.bannerImg,
            name = dto.name,
            type = dto.type,
            content = dto.content
        )

    override fun toDto(user: User): FindAllUserListDto =
            FindAllUserListDto(
                    uuid = user.id,
                    nickname = user.nickname,
                    grade = user.grade,
                    classNum = user.classNum,
                    number = user.number,
                    profileImg = user.profileImg
            )

    override fun toResponse(dto: FindAllUserListDto): FindAllUserListResponse =
            FindAllUserListResponse(
                    uuid = dto.uuid,
                    nickname = dto.nickname,
                    grade = dto.grade,
                    classNum = dto.classNum,
                    number = dto.number,
                    profileImg = dto.profileImg
            )

    override fun toDto(user: User, major: ClubInfoDto, freedom: ClubInfoDto, editorial: List<ClubInfoDto>): UserDetailInfoDto =
            UserDetailInfoDto(
                    nickname = user.nickname,
                    grade = user.grade,
                    classNum = user.classNum,
                    number = user.number,
                    profileImg = user.profileImg,
                    majorClub = major,
                    freedomClub = freedom,
                    editorialClub = editorial
            )

    override fun toResponse(dto: UserDetailInfoDto): UserDetailInfoResponse =
            UserDetailInfoResponse(
                    nickname = dto.nickname,
                    grade = dto.grade,
                    classNum = dto.classNum,
                    number = dto.number,
                    profileImg = dto.profileImg,
                    majorClub = dto.majorClub,
                    freedomClub = dto.freedomClub,
                    editorialClub = dto.editorialClub
            )


    override fun toClubInfoDto(club: Club): ClubInfoDto =
            ClubInfoDto(
                    bannerImg = club.bannerImg,
                    name = club.name
            )
}