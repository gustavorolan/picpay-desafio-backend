package com.picpay_desafio_backend.core.repository

import com.picpay_desafio_backend.core.model.DocumentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DocumentRepository : JpaRepository<DocumentEntity, UUID> {
}