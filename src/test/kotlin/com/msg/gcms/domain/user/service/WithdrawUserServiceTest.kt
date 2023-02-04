package com.msg.gcms.domain.user.service

import com.msg.gcms.domain.club.domain.repository.ClubRepository
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.service.impl.WithdrawUserServiceImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class WithdrawUserServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val userRepository = mockk<UserRepository>()
    val clubRepository = mockk<ClubRepository>()
    val withdrawUserServiceImpl = WithdrawUserServiceImpl(userUtil, userRepository, clubRepository)

    extension(SpringExtension)
    given("withdraw user") {
        val user = TestUtils.data().user().entity()

        `when`("is invoked") {
            every { userUtil.fetchCurrentUser() } returns user
            every { clubRepository.existsByUser(user) } returns false
            every { userRepository.delete(user) } returns Unit
            withdrawUserServiceImpl.execute()

            then("fetchCurrentUser in userUtil should be called") {
                verify(exactly = 1) { userUtil.fetchCurrentUser() }
            }
            then("existsByUser in clubRepository should be called") {
                verify(exactly = 1) { clubRepository.existsByUser(user) }
            }
            then("deleteUser in userRepository should be called") {
                verify(exactly = 1) { userRepository.delete(user) }
            }
        }
    }
}) {
}