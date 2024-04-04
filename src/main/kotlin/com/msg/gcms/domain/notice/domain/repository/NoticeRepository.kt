package com.msg.gcms.domain.notice.domain.repository

import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.notice.domain.entity.Notice
import org.springframework.data.repository.CrudRepository

interface NoticeRepository : CrudRepository<Notice, Long> {
    fun findByClub (club: Club): List<Notice>
}