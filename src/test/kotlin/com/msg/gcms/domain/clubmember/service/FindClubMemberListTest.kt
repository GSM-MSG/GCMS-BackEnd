package com.msg.gcms.domain.clubmember.service

import com.msg.gcms.domain.club.domain.entity.enums.ClubType
import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.presentation.data.dto.ClubTypeDto
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.entity.enums.MemberScope
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.presentation.data.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.service.impl.FindClubMemberListServiceImpl
import com.msg.gcms.domain.clubMember.util.ClubMemberConverter
import com.msg.gcms.domain.clubMember.util.impl.ClubMemberConverterImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import org.springframework.context.annotation.Bean

class FindClubMemberListTest : BehaviorSpec({
    @Bean
    fun clubMemberConverter(): ClubMemberConverter = ClubMemberConverterImpl()

    val clubMemberRepository = mockk<ClubMemberRepository>()
    val clubRepository = mockk<ClubRepository>()
    val userUtil = mockk<UserUtil>()

    val findClubMemberListServiceImpl = FindClubMemberListServiceImpl(
        clubMemberRepository = clubMemberRepository,
        clubRepository = clubRepository,
        clubMemberConverter = clubMemberConverter(),
        userUtil = userUtil
    )
    Given("동아리 안에 부장 1명과 멤버가 5명이 존재 하는 상황에서") {
        val user = (1..5)
            .map { TestUtils.data().user().entity() }
        val type = ClubType.values().random()
        val club = TestUtils.data().club().entity(type)
        val clubHead = TestUtils.data().clubMember().clubMemberDto(user[0], MemberScope.HEAD)
        val clubMember = (2..5)
            .map { TestUtils.data().clubMember().clubMemberDto(user[it], MemberScope.MEMBER) }
        val clubMemberList = mutableListOf<ClubMemberDto>()
            .also { it.add(clubHead) }
        clubMember
            .forEach { clubMemberList.add(it) }
        val clubMemberListDto = ClubMemberListDto(user[0].clubMember.find { it.club == club }!!.scope, clubMemberList)

        When("동아리 멤버 리스트를 요청하면") {
            val result = findClubMemberListServiceImpl.execute(club.id)
        }
    }
})