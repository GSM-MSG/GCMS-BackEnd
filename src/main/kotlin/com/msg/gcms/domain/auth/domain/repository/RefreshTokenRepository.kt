package com.msg.gcms.domain.auth.domain.repository

import com.msg.gcms.domain.auth.domain.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID> {
    fun findByToken(token: String): RefreshToken?

    fun findByUserId(userId: UUID): RefreshToken?
}