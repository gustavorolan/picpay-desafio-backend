package com.picpay_desafio_backend.rest.config

import com.picpay_desafio_backend.core.dto.ErrorResponse
import com.picpay_desafio_backend.core.exception.MainException
import com.picpay_desafio_backend.core.exception.MainHttpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionHandlerAdvice {

    companion object {
        private const val GENERIC_MESSAGE = "Internal Server Error"
        private const val GENERIC_EXCEPTION = "Exception"
    }

    @ExceptionHandler(
        MainHttpException::class,
    )
    fun mainHttpExceptionHandler(exception: MainHttpException): ResponseEntity<ErrorResponse> =
        ResponseEntity<ErrorResponse>(exception.toErrorResponse(), exception.httpStatus)

    @ExceptionHandler(
        Throwable::class,
        RuntimeException::class,
        MainException::class,
    )
    fun throwableHandler(throwable: Throwable): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(throwable.toErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    fun MainHttpException.toErrorResponse(): ErrorResponse = ErrorResponse(
        name = this::class.simpleName ?: GENERIC_EXCEPTION,
        message = this.message ?: GENERIC_MESSAGE,
        httpStatus = this.httpStatus
    )

    fun Throwable.toErrorResponse(): ErrorResponse = ErrorResponse(
        name = this::class.simpleName ?: GENERIC_MESSAGE,
        message = this.message ?: GENERIC_MESSAGE,
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    )
}



