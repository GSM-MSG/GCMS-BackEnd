package com.msg.gcms.domain.attendance.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class AlreadyScheduleExistException : BasicException(ErrorCode.ALREADY_SCHEDULE_EXIST)