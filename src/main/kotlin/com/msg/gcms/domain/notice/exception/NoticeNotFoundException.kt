package com.msg.gcms.domain.notice.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class NoticeNotFoundException: BasicException(ErrorCode.NOTICE_NOT_FOUND) {
}