package com.msg.gcms.global.security.auth

import com.msg.gcms.domain.user.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        AuthDetails(userRepository.findByEmail(username)
            ?: throw RuntimeException())
}
