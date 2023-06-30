package com.msg.gcms.domain.club.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class OpeningApplicationAlreadyExistException: BasicException(ErrorCode.OPENING_APPLICATION_ALREADY_EXIST) {
}