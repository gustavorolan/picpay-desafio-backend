package com.picpay_desafio_backend.core.repository

import com.picpay_desafio_backend.core.model.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface NotificationRepository : JpaRepository<NotificationEntity, UUID> {
    fun findByTransactionId(transactionId: UUID): Optional<NotificationEntity>
    fun findAllByStatus(statusEnum: NotificationEntity.StatusEnum):List<NotificationEntity>
}