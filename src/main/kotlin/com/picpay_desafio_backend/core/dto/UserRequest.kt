package com.picpay_desafio_backend.core.dto

import java.math.BigDecimal

data class UserRequest(
    val fullName: String,

    val email: String,

    val password: String,

    val balance: BigDecimal,

    val documentNumber: String,
)
