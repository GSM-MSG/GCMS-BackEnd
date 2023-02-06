package com.msg.gcms.domain.clubMember.service.impl

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto
import com.msg.gcms.domain.clubMember.service.DelegateHeadService
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DelegateHeadServiceImpl(
    private val clubRepository: ClubRepository,
    private val clubMemberRepository: ClubMemberRepository,
    private val userRepository: UserRepository,
    private val userUtil: UserUtil
) : DelegateHeadService {
    override fun execute(clubId: Long, delegateHeadDto: DelegateHeadDto) {
        val club = (clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException())
        val user = userUtil.fetchCurrentUser()
        if (club.user != user)
            throw HeadNotSameException()
        if (!userRepository.existsById(delegateHeadDto.uuid))
            throw UserNotFoundException()
        val clubMember = clubMemberRepository.findAllByClub(club)
            .find { it.user.id == delegateHeadDto.uuid }
            ?: throw NotClubMemberException()
        updateClub(club, clubMember)
        clubMemberRepository.delete(clubMember)
        clubMemberRepository.save(ClubMember(club = club, user = user))
    }

    private fun updateClub(club: Club, clubMember: ClubMember): Club{
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
            user = clubMember.user,
            isOpened = club.isOpened
        )
        return clubRepository.save(newClub)
    }
}