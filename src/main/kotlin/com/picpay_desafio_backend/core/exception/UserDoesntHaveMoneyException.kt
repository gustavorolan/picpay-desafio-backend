package com.picpay_desafio_backend.core.exception

class UserDoesntHaveMoneyException : BadRequestException("This operation is not Allowed, this user doesnt have sufficient Money!")