package com.msg.gcms.domain.admin.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class NotAceessAdminException : BasicException(ErrorCode.NOT_ACCESS_ADMIN)