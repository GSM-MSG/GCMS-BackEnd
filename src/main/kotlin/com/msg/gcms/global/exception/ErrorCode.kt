package com.msg.gcms.global.exception

enum class ErrorCode(
    val message: String,
    val status: Int
) {
    USER_IS_HEAD("동아리의 부장임", 400),
    FILE_SIZE_OVER("파일의 크기가 5보다 큼", 400),
    CLUB_MEMBER_EXIT_ONESELF("자기 자신을 방출하려 하는 경우", 400),
    CLUB_STATUS_NOT_PENDING("생성대기 중인 동아리가 아닙니다.", 400),


    UNAUTHORIZED("권한이 없음", 401),
    EXPIRED_TOKEN("만료된 토큰", 401),

    CLUB_MEMBER_NON_EXISTENT("동아리 구성원이 아닌 경우", 403),
    NOT_CLUB_DIRECTOR("해당 동아리의 부장이 아님", 403),
    NOT_CLUB_EXIT("해당 동아리를 나갈 수 없음", 403),
    NOT_CLUB_MEMBER("해당 동아리의 구성원이 아님", 403),
    ALREADY_CLUB_MEMBER("이미 동아리 구성원임", 403),
    ALREADY_CLUB_HEAD("이미 동아리 부장임", 403),
    ALREADY_CLUB_APPLICANT("이미 동아리에 신청함", 403),
    DUPLICATE_APPLICANT("같은 타입의 동아리에 이미 신청함", 403),
    NOT_APPLICANT("해당 동아리에 가입 신청하지 않음", 403),
    CLUB_ALREADY_PENDING("해당하는 클럽이 대기중 상태에 있습니다.", 403),
    NOT_ACCESS_ADMIN("해당 관리자 권한을 가진 사람은 접근할 수 없습니다.", 403),
    CLUB_NOT_OPENING("동아리 신청이 열리지 않았습니다.", 403),


    ROLE_NOT_EXIST("역할이 존재하지 않는 경우", 404),
    CLUB_MEMBER_RELEASE_NOT_FOUND("방출하려는 유저가 존재하지 않는 경우", 404),
    USER_NOT_FOUND("해당 유저를 찾을 수 없음", 404),
    CLUB_NOT_FOUND("해당 동아리를 찾을 수 없음", 404),
    DEVICE_TOKEN_NOT_FOUND("디바이스 토큰을 찾을 수 없음", 404),

    ALREADY_CLUB_EXIST("해당 동아리가 이미 존재함", 409),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
}