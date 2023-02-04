package com.msg.gcms.domain.club.service

import com.msg.gcms.domain.club.presentation.data.dto.DetailClubDto

interface DetailClubService {
    fun execute(clubId: Long): DetailClubDto
}