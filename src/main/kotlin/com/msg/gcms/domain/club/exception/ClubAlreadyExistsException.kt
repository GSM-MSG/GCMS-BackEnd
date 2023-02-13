package com.msg.gcms.domain.club.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class ClubAlreadyExistsException : BasicException(ErrorCode.ALREADY_CLUB_EXIST)