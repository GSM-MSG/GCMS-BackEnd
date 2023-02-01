package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import com.msg.gcms.domain.clubMember.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.user.service.impl.FindUserServiceImpl
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.domain.user.utils.impl.UserConverterImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean

class FindUserServiceTest : BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }
    @Bean
    fun userConverter(): UserConverter {
        return UserConverterImpl()
    }
    val clubRepository = mockk<ClubRepository>()
    val clubMemberRepository = mockk<ClubMemberRepository>()
    val userUtils = mockk<UserUtil>()
    val findUserServiceImpl = FindUserServiceImpl(userUtils, clubRepository, clubMemberRepository, clubConverter(), userConverter())

    given("find user") {
        val user = TestUtils.data().user().entity()
        val userDto = TestUtils.data().user().userDto(user)
        `when`("is invoked") {
            every { userUtils.fetchCurrentUser() } returns user
            every { clubRepository.findByUser(user) } returns listOf()
            every { clubMemberRepository.findByUser(user) } returns listOf()
            val result = findUserServiceImpl.execute()

            then("result should not be null") {
                result shouldNotBe null
            }
            then("userUtil should be called") {
                verify(exactly = 1) { userUtils.fetchCurrentUser() }
            }
            then("clubRepository should be called") {
                verify(exactly = 1) { clubRepository.findByUser(user) }
            }
            then("clubMemberRepository should be called") {
                verify(exactly = 1) { clubMemberRepository.findByUser(user) }
            }
            then("result should be same as clubListDto") {
                result shouldBe userDto
            }
        }
    }
})