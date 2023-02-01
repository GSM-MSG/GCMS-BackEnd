package com.msg.gcms.domain.clubMember.domain.repository

import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.repository.CrudRepository

interface ClubMemberRepository : CrudRepository<ClubMember, Long> {
    fun findByUser(user: User): List<ClubMember>
}