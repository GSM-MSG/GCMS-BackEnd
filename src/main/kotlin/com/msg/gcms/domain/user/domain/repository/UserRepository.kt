package com.msg.gcms.domain.user.domain.repository

import com.msg.gcms.domain.auth.domain.Role
import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository: CrudRepository<User, UUID>, UserRepositoryCustom {
    fun findByEmail(email: String) : User?
    fun findByNicknameContaining(name: String): List<User>
    fun findAllByRoles(role: Role): List<User>
}