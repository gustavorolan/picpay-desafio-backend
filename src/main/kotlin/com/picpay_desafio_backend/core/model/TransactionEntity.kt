package com.picpay_desafio_backend.core.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "transaction")
data class TransactionEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    val receiver: UserEntity,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val sender: UserEntity,

    val amount: BigDecimal = BigDecimal.ZERO,

    val retries: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: StatusEnum = StatusEnum.PROGRESS
) {

    enum class StatusEnum {
        PROGRESS, COMPLETED, FAILED
    }
}
