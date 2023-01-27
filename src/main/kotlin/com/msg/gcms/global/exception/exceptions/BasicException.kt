package com.msg.gcms.global.exception.exceptions

import com.msg.gcms.global.exception.ErrorCode

open class BasicException(val errorCode: ErrorCode): RuntimeException()