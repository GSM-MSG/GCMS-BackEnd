package com.msg.gcms.domain.user.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class UserIsHeadException : BasicException(ErrorCode.USER_IS_HEAD)