package com.msg.gcms.domain.image.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class FileSizeOverException : BasicException(ErrorCode.FILE_SIZE_OVER)