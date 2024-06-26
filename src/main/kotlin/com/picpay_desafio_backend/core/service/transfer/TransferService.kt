package com.picpay_desafio_backend.core.service.transfer

import com.picpay_desafio_backend.core.dto.TransferRequest
import com.picpay_desafio_backend.core.model.TransactionEntity

interface TransferService {
    fun send(transferRequest: TransferRequest)
    fun cancel(transaction: TransactionEntity)
    fun finalize(transaction: TransactionEntity)
}