package com.picpay_desafio_backend.core.model

import com.picpay_desafio_backend.core.exception.InvalidDocumentException
import jakarta.persistence.*
import java.util.*
import java.util.regex.Pattern

@Entity
@Table(name = "document")
data class DocumentEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true)
    val number: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: Type,
) {

    enum class Type {
        CPF, CNPJ
    }

    companion object {
        private const val CPF_UNFORMATTED_REGEX: String = "^\\d{11}$"
        private const val CNPJ_UNFORMATTED_REGEX: String = "^\\d{14}$"

        fun getDocumentType(documentNumber: String): Type {
            return when {
                isCPF(documentNumber) -> Type.CPF
                isCNPJ(documentNumber) -> Type.CNPJ
                else -> throw InvalidDocumentException()
            }
        }

        private fun isCNPJ(documentNumber: String): Boolean {
            val cnpjPattern = Pattern.compile(CNPJ_UNFORMATTED_REGEX)
            return cnpjPattern.matcher(documentNumber).matches()
        }

        private fun isCPF(documentNumber: String): Boolean {
            val cpfPattern = Pattern.compile(CPF_UNFORMATTED_REGEX)
            return cpfPattern.matcher(documentNumber).matches()
        }
    }
}