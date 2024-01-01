package com.msg.gcms.domain.admin.service

import javax.servlet.http.HttpServletResponse

interface ClubOpeningApplicationHwpService {
    fun execute(clubId: Long, response: HttpServletResponse): ByteArray
}