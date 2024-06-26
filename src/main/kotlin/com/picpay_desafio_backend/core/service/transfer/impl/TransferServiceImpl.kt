package com.picpay_desafio_backend.core.service.transfer.impl

import com.picpay_desafio_backend.core.client.TransferClient
import com.picpay_desafio_backend.core.dto.TransferRequest
import com.picpay_desafio_backend.core.model.TransactionEntity
import com.picpay_desafio_backend.core.model.UserEntity
import com.picpay_desafio_backend.core.service.transaction.TransactionService
import com.picpay_desafio_backend.core.service.transfer.TransferService
import com.picpay_desafio_backend.core.service.user.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferServiceImpl(
    private val transferClient: TransferClient,
    private val userService: UserService,
    private val transactionService: TransactionService,
) : TransferService {

    @Transactional
    override fun send(transferRequest: TransferRequest) {
        val sender = userService.findById(transferRequest.sender)
        val receiver = userService.findById(transferRequest.receiver)

        sender.canSendMoney(valueToSend = transferRequest.amount, receiver = receiver)

        val senderWithTransfer = sender.copy(
            balance = sender.balance.subtract(transferRequest.amount),
            balanceBlocked = sender.balanceBlocked.add(transferRequest.amount)
        )
        val receiverWithTransfer = receiver.copy(balanceBlocked = receiver.balanceBlocked.add(transferRequest.amount))

        val createNewTransaction = transactionEntity(receiverWithTransfer, senderWithTransfer, transferRequest.amount)

        saveAll(
            transaction = createNewTransaction,
            sender = senderWithTransfer,
            receiver = receiverWithTransfer
        )
    }

    @Transactional
    override fun cancel(transaction: TransactionEntity) {
        val sender = transaction.sender
        val receiver = transaction.receiver
        val amount = transaction.amount

        val senderRefunded = sender.copy(
            balance = sender.balance.add(amount),
            balanceBlocked = sender.balanceBlocked.subtract(amount)
        )
        val receiverRefunded = receiver
            .copy(balanceBlocked = receiver.balanceBlocked.subtract(amount))

        val transactionFailed = transaction.copy(status = TransactionEntity.StatusEnum.FAILED)

        saveAll(
            transaction = transactionFailed,
            sender = senderRefunded,
            receiver = receiverRefunded
        )
    }

    @Transactional
    override fun finalize(transaction: TransactionEntity) {
        val sender = transaction.sender
        val receiver = transaction.receiver
        val amount = transaction.amount

        val senderWithTransfer = sender.copy(
            balanceBlocked = sender.balanceBlocked.subtract(amount)
        )

        val receiverWithTransfer = receiver
            .copy(
                balanceBlocked = sender.balanceBlocked.subtract(amount),
                balance = receiver.balance.add(amount)
            )

        val transactionSuccess = transaction.copy(status = TransactionEntity.StatusEnum.COMPLETED)

        saveAll(
            transaction = transactionSuccess,
            sender = senderWithTransfer,
            receiver = receiverWithTransfer
        )
    }

    private fun saveAll(
        transaction: TransactionEntity,
        sender: UserEntity,
        receiver: UserEntity
    ) {
        transactionService.save(transaction)
        userService.save(sender)
        userService.save(receiver)
    }

    private fun transactionEntity(
        receiver: UserEntity,
        sender: UserEntity,
        amount: BigDecimal
    ) = TransactionEntity(
        receiver = receiver,
        sender = sender,
        status = TransactionEntity.StatusEnum.PROGRESS,
        amount = amount
    )
}