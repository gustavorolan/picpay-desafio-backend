package com.picpay_desafio_backend.core.service.notification.impl

import com.picpay_desafio_backend.core.client.TransferClient
import com.picpay_desafio_backend.core.model.NotificationEntity
import com.picpay_desafio_backend.core.model.TransactionEntity
import com.picpay_desafio_backend.core.repository.NotificationRepository
import com.picpay_desafio_backend.core.service.notification.NotificationService
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val transferClient: TransferClient,
    private val notificationRepository: NotificationRepository
) : NotificationService {

    override fun notify(transactionEntity: TransactionEntity) {
        try {
            transferClient.notify()
        } catch (e: Exception) {
            notificationRepository.save(
                NotificationEntity(
                    transactionId = transactionEntity.id,
                    status = NotificationEntity.StatusEnum.FAILED
                )
            )
        }
    }

    @Async
    @Scheduled(fixedRateString = "\${spring.application.transfer.notify.time_ms}")
    override fun sendNotifications() {
        val notifications = notificationRepository.findAllByStatus(NotificationEntity.StatusEnum.FAILED)
        notifications.forEach {
            try {
                transferClient.notify()
                notificationRepository.save(
                    it.copy(
                        transactionId = it.transactionId,
                        status = NotificationEntity.StatusEnum.FAILED
                    )
                )
            } catch (e: Exception) {
                throw e
            }
        }
    }

}