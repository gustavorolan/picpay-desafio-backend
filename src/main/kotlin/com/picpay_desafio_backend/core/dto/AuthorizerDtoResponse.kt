package com.picpay_desafio_backend.core.dto


data class AuthorizerDtoResponse(
    val status: String = "",
    val statusEnum: StatusEnum = StatusEnum.getStatusByName(status),
    val data: DataAuthorizerDtoResponse
) {
    enum class StatusEnum(val status: String) {
        SUCCESS(status = "success"), FAIL(status = "fail");

        companion object {
            fun getStatusByName(name: String) =
                entries.firstOrNull() { it.status == name }
                    ?:FAIL
        }

    }
}

data class DataAuthorizerDtoResponse(
    val authorization: Boolean,
)