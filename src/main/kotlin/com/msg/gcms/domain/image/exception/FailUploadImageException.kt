package com.g3c1.aidboss.domain.image.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class FailUploadImageException : BasicException(ErrorCode.INTERNAL_SERVER_ERROR)