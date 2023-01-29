package com.msg.gcms.domain.club.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class ClubNotFoundException : BasicException(ErrorCode.CLUB_NOT_FOUND) {
}