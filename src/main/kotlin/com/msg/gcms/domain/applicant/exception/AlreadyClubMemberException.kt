package com.msg.gcms.domain.applicant.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class AlreadyClubMemberException : BasicException(ErrorCode.ALREADY_CLUB_MEMBER)