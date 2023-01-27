package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.Club
import org.springframework.data.repository.CrudRepository

interface ClubRepository : CrudRepository<Club, Long> {
}