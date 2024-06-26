package com.picpay_desafio_backend.core.client

import com.picpay_desafio_backend.core.dto.AuthorizerDtoResponse
import com.picpay_desafio_backend.core.dto.NotifierDtoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "TransferFinalizerClient", url = "\${spring.application.transfer.external.client.url}")
interface TransferClient {

    @GetMapping("/v2/authorize")
    fun authorize(): ResponseEntity<AuthorizerDtoResponse>

    @PostMapping("/v1/notify")
    fun notify(): ResponseEntity<NotifierDtoResponse>
}