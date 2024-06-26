package com.msg.gcms.testUtils

import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.enums.ClubStatus
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.data.dto.ClubListDto
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.club.presentation.data.response.ClubListResponseDto
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User
import kotlin.random.Random
import kotlin.random.Random.Default.nextBoolean

object ClubDataUtil {
    private fun name() = listOf("동아리", "동아리1", "동아리2").random()
    private fun bannerImg() = listOf("이미지", "이미지1" ,"이미지2").random()
    private fun content() = listOf("동아리소개", "동아리소개1" ,"동아리소개2").random()
    private fun notionLink() = listOf("노션링크", "노션링크1", "노션링크2").random()
    private fun teacher() = listOf("선생", "선생1", "선생2").random()
    private fun contact() = listOf("연락쳐", "연락쳐1", "연락처2").random()

    fun entity(type: ClubType) = Club(
        id = Random.nextLong(),
        name = name(),
        bannerImg = bannerImg(),
        content = content(),
        notionLink = notionLink(),
        teacher = teacher(),
        contact = contact(),
        type = type,
        isOpened = nextBoolean(),
        user = TestUtils.data().user().entity(),
        activityImg = listOf(),
        applicant = listOf(),
        clubMember = listOf(),
        clubStatus = ClubStatus.values().random()
    )
    fun entity() = Club(
        id = 1,
        name = name(),
        bannerImg = bannerImg(),
        content = content(),
        notionLink = notionLink(),
        teacher = teacher(),
        contact = contact(),
        type = ClubType.values().random(),
        isOpened = nextBoolean(),
        user = TestUtils.data().user().entity(),
        activityImg = listOf(),
        applicant = listOf(),
        clubMember = listOf()
    )

    fun entity(isOpened: Boolean) = Club(
        id = Random.nextLong(),
        name = name(),
        bannerImg = bannerImg(),
        content = content(),
        notionLink = notionLink(),
        teacher = teacher(),
        contact = contact(),
        type = ClubType.values().random(),
        isOpened = isOpened,
        user = TestUtils.data().user().entity(),
        activityImg = listOf(),
        applicant = listOf(),
        clubMember = listOf()
    )

    fun entity(club: Club, clubMember: List<ClubMember>) = Club(
        id = club.id,
        name = club.name,
        bannerImg = club.bannerImg,
        content = club.content,
        notionLink = club.notionLink,
        teacher = club.teacher,
        contact = club.contact,
        type = club.type,
        isOpened = club.isOpened,
        user = club.user,
        activityImg = listOf(),
        applicant = listOf(),
        clubMember = clubMember
    )

    fun entity(user: User) = Club(
        id = Random.nextLong(),
        name = name(),
        bannerImg = bannerImg(),
        content = content(),
        notionLink = notionLink(),
        teacher = teacher(),
        contact = contact(),
        type = ClubType.values().random(),
        isOpened = true,
        user = user,
        activityImg = listOf(),
        applicant = listOf(),
        clubMember = listOf()
    )

    fun entity(club: Club, applicant: Applicant) = Club(
        id = club.id,
        name = club.name,
        bannerImg = club.bannerImg,
        content = club.content,
        notionLink = club.notionLink,
        teacher = club.teacher,
        contact = club.contact,
        type = club.type,
        isOpened = club.isOpened,
        user = club.user,
        activityImg = listOf(),
        applicant = listOf(applicant),
        clubMember = listOf()
    )

    fun clubListDto(type: ClubType) = ClubListDto(
        id = Random.nextLong(),
        type = type,
        name = name(),
        bannerImg = bannerImg(),
        content = content()
    )
    fun clubTypeDto(type: ClubType) = ClubTypeDto(
        clubType = type
    )
    fun clubListResponseDto(type: ClubType) = ClubListResponseDto(
        id = Random.nextLong(),
        type = type,
        name = name(),
        bannerImg = bannerImg(),
        content = content()
    )
}