package com.spacewalk.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

class HttpException(
    var status: HttpStatus? = null,
    var code: HttpExceptionCode? = null,
    override var message: String ?= null
) : RuntimeException(message)