package com.msg.gcms.global.security.auth

import com.msg.gcms.domain.admin.domain.entity.Admin
import com.msg.gcms.domain.auth.domain.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AdminDetails(
    private val admin: Admin
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority(Role.ROLE_ADMIN.name))

    override fun getPassword(): String? = null

    override fun getUsername(): String = admin.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}