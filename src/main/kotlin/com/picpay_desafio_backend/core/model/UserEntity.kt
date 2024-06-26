package com.picpay_desafio_backend.core.model

import com.picpay_desafio_backend.core.exception.CantSendMoneyException
import com.picpay_desafio_backend.core.exception.InvalidDocumentException
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "picpay_user")
data class UserEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val fullName: String = "",

    val email: String = "",

    val password: String = "",

    val balance: BigDecimal = BigDecimal.ZERO,

    val balanceBlocked: BigDecimal = BigDecimal.ZERO,

    @JoinColumn(name = "document_id", unique = true, nullable = false)
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val documentEntity: DocumentEntity,

) {

    companion object {
        private val sendMoneyRule = mapOf(
            DocumentEntity.Type.CPF to listOf(DocumentEntity.Type.CPF, DocumentEntity.Type.CNPJ),
            DocumentEntity.Type.CNPJ to listOf()
        )
    }

    fun canSendMoney(receiver: UserEntity, valueToSend: BigDecimal) {
        sendMoneyTypeRule(receiver)
        hasMoney(valueToSend)
    }

    private fun sendMoneyTypeRule(receiver: UserEntity) {
        val senderType = this.documentEntity.type
        val canSend = sendMoneyRule[senderType]
            ?.contains(receiver.documentEntity.type)
            ?: throw InvalidDocumentException()
        if (!canSend) throw CantSendMoneyException()
    }

    private fun hasMoney(valueToSend: BigDecimal) {
        if (this.balance < valueToSend) throw CantSendMoneyException()
    }
}

