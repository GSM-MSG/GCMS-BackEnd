package com.msg.gcms.global.exception.exceptions

import com.msg.gcms.global.exception.ErrorCode

class BasicException(val errorCode: ErrorCode): RuntimeException()