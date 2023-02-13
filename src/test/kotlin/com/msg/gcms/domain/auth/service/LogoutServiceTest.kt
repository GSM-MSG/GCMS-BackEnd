package com.msg.gcms.domain.auth.service

import com.msg.gcms.domain.auth.domain.repository.RefreshTokenRepository
import com.msg.gcms.domain.auth.service.impl.LogoutServiceImpl
import com.msg.gcms.global.util.UserUtil
import com.msg.gcms.testUtils.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class LogoutServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()

    val logoutService = LogoutServiceImpl(
        userUtil = userUtil,
        refreshTokenRepository = refreshTokenRepository
    )

    given("유저와 리프레시토큰 엔티티가 주어지면") {
        val user = TestUtils.data().user().entity()
        every { userUtil.fetchCurrentUser() } returns user
        val refreshToken = TestUtils.data().auth().entity(userId = user.id)
        every { refreshTokenRepository.findByUserId(userId = user.id) } returns refreshToken
        `when`("logoutService를 실행하고") {
            every { refreshTokenRepository.delete(refreshToken) } returns Unit
            logoutService.execute()
            then("그때 delete 쿼리가 한번 실행되어야 함") {
                verify(exactly = 1) { refreshTokenRepository.delete(refreshToken) }
            }
        }
    }
})