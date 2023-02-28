package com.msg.gcms.domain.admin.domain.repository

import com.msg.gcms.domain.admin.domain.entity.Admin
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AdminRepository : CrudRepository<Admin, UUID> {
    fun findByEmail(email: String): Admin?
}