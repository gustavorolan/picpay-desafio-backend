package com.picpay_desafio_backend.core.service.user

import com.picpay_desafio_backend.core.dto.UserRequest
import com.picpay_desafio_backend.core.dto.UserResponse
import com.picpay_desafio_backend.core.model.UserEntity
import java.util.UUID

interface UserService {
    fun findById(userId: UUID): UserEntity
    fun save(userEntity: UserEntity): UserEntity
    fun createNewUser(userRequest: UserRequest): Any
    fun findResponseById(userId: UUID): UserResponse
}