package com.picpay_desafio_backend.core.service.transaction

import com.picpay_desafio_backend.core.model.TransactionEntity

interface TransactionService {
    fun save(transaction: TransactionEntity)
    fun findAllStatusInProgress(): List<TransactionEntity>
}