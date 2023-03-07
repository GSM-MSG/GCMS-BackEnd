package com.msg.gcms.domain.clubMember.util

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class UpdateClubHeadUtil(
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
) {
    fun updateClubHead(club: Club, clubMember: ClubMember, user: User): Club {
        val newClubMember = ClubMember(club = club, user = user)
        clubMemberRepository.save(newClubMember)
        clubMemberRepository.delete(clubMember)
        return clubRepository.save(
            Club(
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
                user = clubMember.user,
                isOpened = club.isOpened,
                clubStatus = club.clubStatus
            )
        )
    }
}