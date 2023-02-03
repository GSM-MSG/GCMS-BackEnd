package com.msg.gcms.global.exception

enum class ErrorCode(
    val message: String,
    val status: Int
) {
    UNAUTHORIZED("권한이 없음", 401),
    EXPIRED_TOKEN("만료된 토큰", 401),

    CLUB_MEMBER_NON_EXISTENT("동아리 구성원이 아닌 경우", 403),
    NOT_CLUB_DIRECTOR("해당 동아리의 부장이 아님", 403),

    USER_NOT_FOUND("해당 유저를 찾을 수 없음", 404),
    CLUB_NOT_FOUND("해당 동아리를 찾을 수 없음", 404),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
}