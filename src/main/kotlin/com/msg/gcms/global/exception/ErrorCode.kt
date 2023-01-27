package com.msg.gcms.global.exception

enum class ErrorCode(
    val message: String,
    val status: Int
) {
    CLUB_NOT_FOUND("동아리 를 찾을 수 없습니다.", 404)
}