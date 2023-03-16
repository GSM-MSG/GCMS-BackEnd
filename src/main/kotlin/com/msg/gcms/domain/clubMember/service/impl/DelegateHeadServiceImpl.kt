package com.msg.gcms.domain.clubMember.service.impl

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.exception.ClubNotFoundException
import com.msg.gcms.domain.club.exception.HeadNotSameException
import com.msg.gcms.domain.club.exception.NotClubMemberException
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.presentation.data.dto.ChangeHeadDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.DelegateHeadDto
import com.msg.gcms.domain.clubMember.service.DelegateHeadService
import com.msg.gcms.domain.clubMember.util.UpdateClubHeadUtil
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.fcm.enums.SendType
import com.msg.gcms.global.util.MessageSendUtil
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
    private val updateClubHeadUtil: UpdateClubHeadUtil,
    private val userUtil: UserUtil,
    private val messageSendUtil: MessageSendUtil
) : DelegateHeadService {
    override fun execute(clubId: Long, delegateHeadDto: DelegateHeadDto): ChangeHeadDto {
        val club = (clubRepository.findByIdOrNull(clubId)
            ?: throw ClubNotFoundException())
        val user = userUtil.fetchCurrentUser()
        val head = club.user
        if (head != user && !user.roles.contains(Role.ROLE_ADMIN))
            throw HeadNotSameException()
        if (!userRepository.existsById(delegateHeadDto.uuid))
            throw UserNotFoundException()
        val clubMember = clubMemberRepository.findAllByClub(club)
            .find { it.user.id == delegateHeadDto.uuid }
            ?: throw NotClubMemberException()
        updateClubHeadUtil.updateClubHead(club, clubMember, head)
        messageSendUtil.send(clubMember.user, "부장 위임", "${club.name}의 부장으로 위임되셨습니다.", SendType.CLUB)
        return ChangeHeadDto(clubMember.user, head)
    }
}