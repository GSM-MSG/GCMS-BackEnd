package com.msg.gcms.domain.user.domain.entity

import java.util.UUID
import javax.persistence.*


@Entity
class DeviceToken (
    @Id
    val userId: UUID,

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
    val user: User,

    val token: String,
)