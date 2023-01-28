package com.msg.gcms.global.security.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class InvalidTokenException : BasicException(ErrorCode.UNAUTHORIZED) {
}