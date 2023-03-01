package com.msg.gcms.domain.auth.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class RoleNotExistException : BasicException(ErrorCode.ROLE_NOT_EXIST) {
}