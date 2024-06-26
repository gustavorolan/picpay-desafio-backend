package com.picpay_desafio_backend.core.repository

import com.picpay_desafio_backend.core.model.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {
    fun findAllByStatus(status: TransactionEntity.StatusEnum): List<TransactionEntity>
}