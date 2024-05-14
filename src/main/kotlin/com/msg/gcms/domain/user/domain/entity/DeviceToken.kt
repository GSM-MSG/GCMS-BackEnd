package com.msg.gcms.domain.user.domain.entity

import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.UUID
import javax.persistence.*


@Entity
@DynamicUpdate
class DeviceToken (
    @Id
    val userId: UUID,

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
    val user: User,

    val token: String?,
)