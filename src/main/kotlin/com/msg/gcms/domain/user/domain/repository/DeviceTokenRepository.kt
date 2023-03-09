package com.msg.gcms.domain.user.domain.repository

import com.msg.gcms.domain.user.domain.entity.DeviceToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface DeviceTokenRepository: CrudRepository<DeviceToken, UUID> {
}