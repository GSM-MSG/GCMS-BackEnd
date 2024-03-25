package com.msg.gcms.domain.notice.domain.repository

import com.msg.gcms.domain.notice.domain.entity.Notice
import org.springframework.data.repository.CrudRepository

interface NoticeRepository : CrudRepository<Notice, Long>