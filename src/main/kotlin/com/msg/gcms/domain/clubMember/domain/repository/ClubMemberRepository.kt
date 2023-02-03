package com.msg.gcms.domain.clubMember.domain.repository

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.user.domain.entity.User
import org.springframework.data.repository.CrudRepository

interface ClubMemberRepository : CrudRepository<ClubMember, Long> {
    fun findAllByClub(club: Club): List<ClubMember>
    fun findByUser(user: User): List<ClubMember>
    fun deleteByClub(club: Club)
}