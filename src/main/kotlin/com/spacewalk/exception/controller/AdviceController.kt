package com.spacewalk.exception.controller

import com.spacewalk.exception.HttpException
import com.spacewalk.exception.dto.ErrorResDto
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import java.lang.RuntimeException

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(HttpException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpExceptionHandler(e: HttpException): Mono<ErrorResDto> {
        return Mono.just(ErrorResDto(message = e.message, code = e.code.toString()))
    }
}