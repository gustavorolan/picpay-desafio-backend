package com.picpay_desafio_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@EnableFeignClients
@EnableScheduling
@SpringBootApplication

class PicpayDesafioBackendApplication

fun main(args: Array<String>) {
	runApplication<PicpayDesafioBackendApplication>(*args)
}
