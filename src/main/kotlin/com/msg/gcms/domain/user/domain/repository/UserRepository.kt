package com.msg.gcms.domain.user.domain.repository

import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UserRepository: CrudRepository<User, UUID> {
    fun findByEmail(email: String) : User?
}