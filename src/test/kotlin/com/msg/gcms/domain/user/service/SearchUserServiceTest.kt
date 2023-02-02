package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.presentaion.data.dto.SearchRequirementDto
import com.msg.gcms.domain.user.service.impl.SearchUserServiceImpl
import com.msg.gcms.domain.user.utils.UserConverter
import com.msg.gcms.domain.user.utils.impl.UserConverterImpl
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean

class SearchUserServiceTest : BehaviorSpec({
    @Bean
    fun userConverter(): UserConverter {
        return UserConverterImpl()
    }
    val userRepository = mockk<UserRepository>()
    val clubRepository = mockk<ClubRepository>()
    val searchUserServiceImpl = SearchUserServiceImpl(userRepository, clubRepository, userConverter())

    given("search user With Type Is MAJOR OR FREEDOM") {
        val size = (1..100).random()
        val type = listOf(ClubType.MAJOR, ClubType.FREEDOM).random()
        val name = TestUtils.data().user().entity().nickname
        val dto = SearchRequirementDto(type, name)
        val user = (1..size)
            .map{ TestUtils.data().user().entity() }
        val searchUserDto = user
            .map { TestUtils.data().user().searchUserDto(it) }

        `when`("is invoked") {
            every { userRepository.findUserNotJoin(type, name) } returns user
            every { clubRepository.existsByUser(any()) } returns true
            val result = searchUserServiceImpl.execute(dto)

            then("result should not be null") {
                result shouldNotBe null
            }
            then("userUtil should be called") {
                verify(exactly = 1) { userRepository.findUserNotJoin(type, name) }
            }
            then("result should be same as userDto") {
                result shouldBe searchUserDto
            }
        }
    }

    given("search user With Type Is EDITORIAL") {
        val size = (1..100).random()
        val type = ClubType.EDITORIAL
        val name = TestUtils.data().user().entity().nickname
        val dto = SearchRequirementDto(type, name)
        val user = (1..size).map{ TestUtils.data().user().entity() }
        val searchUserDto = user
            .map{ TestUtils.data().user().searchUserDto(it) }

        `when`("is invoked") {
            every { userRepository.findByNicknameContaining(name) } returns user
            val result = searchUserServiceImpl.execute(dto)

            then("result should not be null") {
                result shouldNotBe null
            }
            then("userUtil should be called") {
                verify(exactly = 1) { userRepository.findByNicknameContaining(name) }
            }
            then("result should be same as userDto") {
                result shouldBe searchUserDto
            }
        }
    }
})