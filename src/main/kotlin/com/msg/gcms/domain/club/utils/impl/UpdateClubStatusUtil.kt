package com.msg.gcms.domain.club.utils.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import org.springframework.stereotype.Component

@Component
class UpdateClubStatusUtil(
    private val clubRepository: ClubRepository
) {
    fun changeIsOpened(club: Club, isOpened: Boolean) {
        val newClub = Club(
            id = club.id,
            activityImg = club.activityImg,
            applicant = club.applicant,
            bannerImg = club.bannerImg,
            clubMember = club.clubMember,
            contact = club.contact,
            content = club.content,
            name = club.name,
            notionLink = club.notionLink,
            teacher = club.teacher,
            type = club.type,
            user = club.user,
            isOpened = isOpened
        )
        clubRepository.save(newClub)
    }
}