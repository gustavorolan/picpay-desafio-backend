package com.picpay_desafio_backend.core.service.user.impl

import com.picpay_desafio_backend.core.dto.UserRequest
import com.picpay_desafio_backend.core.dto.UserResponse
import com.picpay_desafio_backend.core.exception.UserNotFoundException
import com.picpay_desafio_backend.core.model.DocumentEntity
import com.picpay_desafio_backend.core.model.UserEntity
import com.picpay_desafio_backend.core.repository.DocumentRepository
import com.picpay_desafio_backend.core.repository.UserRepository
import com.picpay_desafio_backend.core.service.user.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val documentRepository: DocumentRepository,
) : UserService {
    override fun findById(userId: UUID): UserEntity =
        userRepository.findById(userId).orElseThrow {
            UserNotFoundException()
        }

    override fun findResponseById(userId: UUID): UserResponse =
        findById(userId)
            .toResponse()

    override fun save(userEntity: UserEntity): UserEntity {
        return userRepository.save(userEntity)
    }

    override fun createNewUser(userRequest: UserRequest): UUID =
        save(userRequest.toEntity()).id


    private fun UserRequest.toEntity() =
        UserEntity(
            fullName = this.fullName,
            email = this.email,
            password = this.password,
            balance = this.balance,
            documentEntity = DocumentEntity(
                number = this.documentNumber,
                type = DocumentEntity.getDocumentType(this.documentNumber)
            )
        )




    private fun UserEntity.toResponse() =
        UserResponse(
            fullName = this.fullName,
            email = this.email,
            balance = this.balance
        )
}