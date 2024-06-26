package com.picpay_desafio_backend.core.exception

import org.springframework.http.HttpStatus

open class MainException(
    message: String,
) : RuntimeException(message)

open class MainHttpException(
    message: String,
    val httpStatus: HttpStatus
) : RuntimeException(message)

open class UnauthorizedException(message: String) : MainHttpException(
    message,
    HttpStatus.UNAUTHORIZED
)

open class BadRequestException(message: String) : MainHttpException(
    message,
    HttpStatus.BAD_REQUEST
)

open class NotFoundException(message: String) : MainHttpException(
    message,
    HttpStatus.NOT_FOUND
)