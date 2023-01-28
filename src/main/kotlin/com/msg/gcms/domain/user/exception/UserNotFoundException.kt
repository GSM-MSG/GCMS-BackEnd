package com.msg.gcms.domain.user.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class UserNotFoundException: BasicException(ErrorCode.USER_NOT_FOUND)