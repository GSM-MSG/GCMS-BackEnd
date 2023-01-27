package com.msg.gcms.global.exception

enum class ErrorCode(
    val message: String,
    val status: Int
) {
    UNAUTHORIZED("권한이 없음", 401),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
}