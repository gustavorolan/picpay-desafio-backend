package com.picpay_desafio_backend.rest

import com.picpay_desafio_backend.core.dto.UserRequest
import com.picpay_desafio_backend.core.dto.UserResponse
import com.picpay_desafio_backend.core.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v0/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping()
    fun createNewUser(
        @RequestBody userRequest: UserRequest,
    ): ResponseEntity<String> {
        val id = userService.createNewUser(userRequest)
        return ResponseEntity
            .created(URI("/$id"))
            .body(id.toString())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        val userResponse = userService.findResponseById(id)
        return ResponseEntity.ok(userResponse)
    }
}
