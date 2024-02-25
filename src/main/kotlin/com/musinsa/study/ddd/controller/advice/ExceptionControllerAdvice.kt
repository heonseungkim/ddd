package com.musinsa.study.ddd.controller.advice

import com.musinsa.study.ddd.controller.dto.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementExceptionException(e: NoSuchElementException): ErrorResponse {
        return ErrorResponse(
            errorCode = 404,
            message = e.message,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        e.printStackTrace()
        return ErrorResponse(
            errorCode = 500,
            message = e.message,
        )
    }
}
