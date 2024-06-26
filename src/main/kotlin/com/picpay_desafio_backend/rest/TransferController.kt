package com.picpay_desafio_backend.rest

import com.picpay_desafio_backend.core.dto.TransferRequest
import com.picpay_desafio_backend.core.service.transfer.TransferService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v0/transfer")
class TransferController(
    private val transferService: TransferService
) {

    @PostMapping()
    fun sendMoney(
        @RequestBody transferRequest: TransferRequest
    ): ResponseEntity<String> {
        transferService.send(transferRequest)
        return ResponseEntity.ok().build()
    }
}
