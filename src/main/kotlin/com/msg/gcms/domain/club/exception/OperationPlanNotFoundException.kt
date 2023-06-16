package com.msg.gcms.domain.club.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class OperationPlanNotFoundException : BasicException(ErrorCode.OPERATION_PLAN_NOT_FOUND) {
}