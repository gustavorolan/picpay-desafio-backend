package com.picpay_desafio_backend.core.service.notification

import com.picpay_desafio_backend.core.model.TransactionEntity

interface NotificationService {
   fun notify(transactionEntity: TransactionEntity)
   fun sendNotifications()
}