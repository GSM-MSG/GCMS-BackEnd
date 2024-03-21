package com.msg.gcms.domain.attendance.domain.enums

import java.time.LocalTime

enum class Period(
    val value: Int,
    val time: LocalTime
) {
    FIRST(1, "08:40:00".toTime()),
    SECOND(2, "09:40:00".toTime()),
    THIRD(3, "10:40:00".toTime()),
    FOURTH(4, "11:40:00".toTime()),
    FIVETH(5, "13:30:00".toTime()),
    SIXTH(6, "14:30:00".toTime()),
    SEVENTH(7, "15:30:00".toTime()),
    EIGHTTH(8, "16:40:00".toTime()),
    NINTH(9, "17:40:00".toTime()),
    TENTH(10, "19:30:00".toTime()),
    ELEVENTH(11, "20:30:00".toTime()),
    ;
}

private fun String.toTime() =
    LocalTime.parse(this)