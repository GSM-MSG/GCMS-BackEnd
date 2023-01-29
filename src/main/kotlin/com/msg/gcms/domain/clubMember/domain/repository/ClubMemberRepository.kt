package com.msg.gcms.domain.clubMember.domain.repository

import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import org.springframework.data.repository.CrudRepository

interface ClubMemberRepository : CrudRepository<ClubMember, Long> {
}