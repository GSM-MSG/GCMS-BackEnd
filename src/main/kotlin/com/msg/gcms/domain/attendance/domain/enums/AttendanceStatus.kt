package com.msg.gcms.domain.attendance.domain.enums

enum class AttendanceStatus(
    val description: String,
    val sign: String
) {
    NOT_ATTENDED("미출석","-"),
    ATTENDED("출석","O"),
    LATE("지각","△"),
    REASONABLE_ABSENT("인정결석","."),
    ABSENT("무단결석","X")
    ;
}