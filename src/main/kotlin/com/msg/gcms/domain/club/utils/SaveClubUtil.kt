package com.msg.gcms.domain.club.utils

import com.msg.gcms.domain.club.domain.entity.ActivityImg
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ActivityImgRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class SaveClubUtil(
    private val clubRepository: ClubRepository,
    private val activityImgRepository: ActivityImgRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userRepository: UserRepository,
){
    fun saveClub(club: Club, clubDto: ClubDto){
        clubRepository.save(club)
        val activityImgs = clubDto.activityImgs
            .map { ActivityImg(image = it, club =  club) }
        activityImgRepository.saveAll(activityImgs)
        val users = clubDto.member
            .map { findById(it) }
            .map { ClubMember(scope = MemberScope.MEMBER, club = club, user = it) }
        clubMemberRepository.saveAll(users)
    }

    private fun findById(it: UUID): User =
        userRepository.findById(it)
            .orElseThrow { throw UserNotFoundException() }
}