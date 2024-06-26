package com.picpay_desafio_backend.core.service.transfer.impl

import com.picpay_desafio_backend.core.client.TransferClient
import com.picpay_desafio_backend.core.dto.AuthorizerDtoResponse
import com.picpay_desafio_backend.core.model.TransactionEntity
import com.picpay_desafio_backend.core.service.notification.NotificationService
import com.picpay_desafio_backend.core.service.transaction.TransactionService
import com.picpay_desafio_backend.core.service.transfer.FinalizeTransferService
import com.picpay_desafio_backend.core.service.transfer.TransferService
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class FinalizeTransferServiceImpl(
    private val transactionService: TransactionService,
    private val transferService: TransferService,
    private val transferClient: TransferClient,
    private val notificationService: NotificationService,
    @Value("\${spring.application.transfer.transaction.retries}")
    private val maxRetries: String

) : FinalizeTransferService {

    @Async
    @Scheduled(fixedRateString = "\${spring.application.transfer.transaction.tempo_ms}")
    override fun finalizeTransactions() {
        val transactions = transactionService.findAllStatusInProgress()

        transactions.forEach {
            finalizeTransaction(it)
        }
    }

    @Async
    fun finalizeTransaction(transaction: TransactionEntity) {
        val response = transferClient.authorize()
        val responseStatus = response.body?.statusEnum ?: AuthorizerDtoResponse.StatusEnum.FAIL
        if (responseStatus == AuthorizerDtoResponse.StatusEnum.SUCCESS) {
            transferService.finalize(transaction)
            notificationService.notify(transaction)
        } else {
            increaseRetryOrCancel(transaction)
        }
    }

    @Async
    fun increaseRetryOrCancel(transaction: TransactionEntity) {
        if (transaction.retries <= maxRetries.toInt()) {
            val transactionTryAgain = transaction.copy(retries = transaction.retries + 1)
            transactionService.save(transactionTryAgain)
        } else {
            transferService.cancel(transaction)
        }
    }
}