package com.msg.gcms.domain.auth.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class AuthorityNotExistException : BasicException(ErrorCode.AUTHORITY_NOT_EXIST) {
}