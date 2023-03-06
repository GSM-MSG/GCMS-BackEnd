package com.msg.gcms.domain.auth.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.UUID

@RedisHash(value = "refreshToken", timeToLive = 60L * 60 * 24 * 7)
class RefreshToken(
    @Indexed
    val userId: UUID? = null,
    @Id
    @Indexed
    val token: String,
)