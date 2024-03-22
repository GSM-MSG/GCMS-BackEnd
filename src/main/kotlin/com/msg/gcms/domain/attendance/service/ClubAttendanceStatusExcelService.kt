package com.msg.gcms.domain.attendance.service

import java.time.LocalDate

interface ClubAttendanceStatusExcelService {
    fun execute(currentDate: LocalDate): ByteArray
}