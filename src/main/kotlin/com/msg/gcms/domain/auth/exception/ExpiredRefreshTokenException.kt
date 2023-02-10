package com.msg.gcms.domain.auth.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class ExpiredRefreshTokenException : BasicException(ErrorCode.EXPIRED_TOKEN){
}