package com.msg.gcms.global.exception

enum class ErrorCode(
    val message: String,
    val status: Int
) {
    CLUB_MEMBER_NON_EXISTENT("동아리 구성원이 아닌 경우", 400),
    UNAUTHORIZED("권한이 없음", 401),
    EXPIRED_TOKEN("만료된 토큰", 401),

    USER_NOT_FOUND("해당 유저를 찾을 수 없음", 404),
    CLUB_NOT_FOUND("동아리를 찾을 수 없습니다.", 404),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
}