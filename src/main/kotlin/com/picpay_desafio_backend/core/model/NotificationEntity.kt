package com.picpay_desafio_backend.core.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "notification")
data class NotificationEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true)
    val transactionId: UUID = UUID.randomUUID(),

    val retries: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: StatusEnum,
) {
    enum class StatusEnum {
        COMPLETED, FAILED
    }
}

