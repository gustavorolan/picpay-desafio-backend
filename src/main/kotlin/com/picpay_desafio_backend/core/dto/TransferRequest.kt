package com.picpay_desafio_backend.core.dto

import java.math.BigDecimal
import java.util.UUID

data class TransferRequest(
   val amount:BigDecimal,
   val sender: UUID,
    val receiver: UUID,
)
