package com.msg.gcms.global.security.auth

import com.msg.gcms.domain.admin.domain.repository.AdminRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminDetailsService(
    private val adminRepository: AdminRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val admin = adminRepository.findByEmail(email) ?: TODO("throw AdminNotFoundException")
        return AdminDetails(admin)
    }
}