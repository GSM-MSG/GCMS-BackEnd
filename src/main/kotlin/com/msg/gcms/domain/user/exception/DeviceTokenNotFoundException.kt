package com.msg.gcms.domain.user.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class DeviceTokenNotFoundException : BasicException(ErrorCode.DEVICE_TOKEN_NOT_FOUND)