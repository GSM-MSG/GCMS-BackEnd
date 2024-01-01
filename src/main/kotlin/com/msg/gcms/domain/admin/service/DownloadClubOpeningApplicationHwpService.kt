package com.msg.gcms.domain.admin.service

interface DownloadClubOpeningApplicationHwpService {
    fun execute(clubId: Long): ByteArray
}