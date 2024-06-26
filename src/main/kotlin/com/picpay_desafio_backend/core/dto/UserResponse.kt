package com.picpay_desafio_backend.core.dto

import java.math.BigDecimal

data class UserResponse(
    val fullName: String,

    val email: String,

    val balance: BigDecimal,
)
