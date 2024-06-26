package com.picpay_desafio_backend.core.dto

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val name:String,
    val message: String,
    val httpStatus: HttpStatus
)
