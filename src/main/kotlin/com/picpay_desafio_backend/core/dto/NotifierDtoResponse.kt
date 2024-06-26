package com.picpay_desafio_backend.core.dto


data class NotifierDtoResponse(
    val status: String = "",
    val statusEnum: StatusEnum = StatusEnum.getStatusByName(status),
    val data: DataNotifierDtoResponse
) {
    enum class StatusEnum(val status: String) {
        SUCCESS(status = "success"), FAIL(status = "fail");

        companion object {
            fun getStatusByName(name: String) =
                entries.firstOrNull() { it.status == name }
                    ?: FAIL
        }

    }
}

data class DataNotifierDtoResponse(
    val message: String,
)