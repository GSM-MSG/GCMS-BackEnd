package com.msg.gcms.global.util

import com.msg.gcms.domain.user.domain.entity.User
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import com.msg.gcms.global.security.auth.AuthDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: UserRepository
) {
    fun fetchCurrentUser(): User {
        val email = fetchUserEmail()
        return fetchUserByEmail(email)
    }

    fun fetchUserByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw UserNotFoundException()
    fun fetchUserEmail(): String {
        val principal = SecurityContextHolder.getContext().authentication.principal

        val email = if (principal is UserDetails) {
            (principal as AuthDetails).username
        } else {
            principal.toString()
        }

        return email
    }
}
