package com.msg.gcms.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.ErrorResponse
import com.msg.gcms.global.exception.exceptions.BasicException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BasicException) {
            log.error(e.message)
            sendError(response, e.errorCode)
        } catch (e: Exception) {
            log.error(e.message)
            response.characterEncoding = "UTF-8"
            sendError(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    private fun sendError(res: HttpServletResponse, errorCode: ErrorCode) {
        val errorResponse = ErrorResponse(errorCode.message, errorCode.status)
        val responseString = objectMapper!!.writeValueAsString(errorResponse)
        res.status = errorCode.status
        res.contentType = MediaType.APPLICATION_JSON_VALUE
        res.writer.write(responseString)
    }
}