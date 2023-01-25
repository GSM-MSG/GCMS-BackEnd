package com.msg.gcms.global.exception.handler

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.ErrorResponse
import com.msg.gcms.global.exception.exceptions.BasicException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BasicException::class)
    fun globalExceptionHandler(e:BasicException) : ResponseEntity<ErrorResponse> {
        val errorCode: ErrorCode = e.errorCode
        return ResponseEntity(
            ErrorResponse(status = errorCode.status, message = errorCode.message),
            HttpStatus.valueOf(errorCode.status)
        )
    }
}