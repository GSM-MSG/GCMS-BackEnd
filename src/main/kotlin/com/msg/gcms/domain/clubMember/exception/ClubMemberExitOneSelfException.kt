package com.msg.gcms.domain.clubMember.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class ClubMemberExitOneSelfException : BasicException(ErrorCode.CLUB_MEMBER_EXIT_ONESELF) {
}