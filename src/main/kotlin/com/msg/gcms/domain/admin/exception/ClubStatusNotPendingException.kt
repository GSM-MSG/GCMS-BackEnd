package com.msg.gcms.domain.admin.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class ClubStatusNotPendingException : BasicException(ErrorCode.CLUB_STATUS_NOT_PENDING)