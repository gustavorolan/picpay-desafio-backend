package com.picpay_desafio_backend.core.service.transaction.impl

import com.picpay_desafio_backend.core.model.TransactionEntity
import com.picpay_desafio_backend.core.repository.TransactionRepository
import com.picpay_desafio_backend.core.service.transaction.TransactionService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository
) : TransactionService {

    override fun save(transaction: TransactionEntity) {
        transactionRepository.save(transaction)
    }

    override fun findAllStatusInProgress(): List<TransactionEntity> =
        transactionRepository.findAllByStatus(TransactionEntity.StatusEnum.PROGRESS)

}