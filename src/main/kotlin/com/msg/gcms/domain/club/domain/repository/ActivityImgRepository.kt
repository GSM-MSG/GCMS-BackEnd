package com.msg.gcms.domain.club.domain.repository

import com.msg.gcms.domain.club.domain.entity.ActivityImg
import com.msg.gcms.domain.club.domain.entity.Club
import org.springframework.data.repository.CrudRepository

interface ActivityImgRepository : CrudRepository<ActivityImg, Long> {
    fun deleteByClub(club: Club)
    fun findAllByClub(club: Club): List<ActivityImg>
}