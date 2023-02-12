package com.msg.gcms.global.exception

enum class ErrorCode(
    val message: String,
    val status: Int
) {
    USER_IS_HEAD("동아리의 부장임", 400),
    FILE_SIZE_OVER("파일의 크기가 4보다 큼", 400),

    UNAUTHORIZED("권한이 없음", 401),
    EXPIRED_TOKEN("만료된 토큰", 401),

    CLUB_MEMBER_NON_EXISTENT("동아리 구성원이 아닌 경우", 403),
    NOT_CLUB_DIRECTOR("해당 동아리의 부장이 아님", 403),
    NOT_CLUB_EXIT("해당 동아리를 나갈 수 없음", 403),
    NOT_CLUB_MEMBER("해당 동아리의 구성원이 아님", 403),
    ALREADY_CLUB_MEMBER("이미 동아리 구성원임", 403),
    DUPLICATE_APPLICANT("같은 타입의 동아리에 이미 신청함", 403),

    USER_NOT_FOUND("해당 유저를 찾을 수 없음", 404),
    CLUB_NOT_FOUND("해당 동아리를 찾을 수 없음", 404),

    ALREADY_CLUB_EXIST("해당 동아리가 이미 존재함", 409),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
}