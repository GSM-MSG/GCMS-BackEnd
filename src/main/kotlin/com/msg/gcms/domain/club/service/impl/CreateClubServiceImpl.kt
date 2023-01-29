package com.msg.gcms.domain.club.service.impl

import com.msg.gcms.domain.club.domain.entity.ActivityImg
import com.msg.gcms.domain.club.domain.repository.ActivityImgRepository
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.service.CreateClubService
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.enums.MemberScope
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CreateClubServiceImpl(
    private val clubRepository: ClubRepository,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
    private val activityImgRepository: ActivityImgRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val clubConverter: ClubConverter,
) : CreateClubService {
    override fun execute(clubDto: ClubDto) {
        val currentUser = userUtil.fetchCurrentUser()
        val club = clubConverter.toEntity(clubDto, currentUser)
        clubRepository.save(club)
        val activityImgs = clubDto.activityImgs
            .map {
                ActivityImg(image = it, club =  club)
            }
        activityImgRepository.saveAll(activityImgs)
        val users = clubDto.member
            .map {
                userRepository.findById(it)
                    .orElseThrow { throw UserNotFoundException() }
            }
            .map {
                ClubMember(scope = MemberScope.MEMBER, club = club, user = it)
            }
        clubMemberRepository.saveAll(users)
    }
}