package com.spacewalk.advice

import com.spacewalk.KotlinSpaceWorkApplication
import com.spacewalk.exception.HttpException
import com.spacewalk.exception.dto.ErrorResDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono
import java.lang.RuntimeException

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(HttpException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpExceptionHandler(e: HttpException): Mono<ErrorResDto> {
        return Mono.just(ErrorResDto(message = e.message, code = e.code.toString()))
    }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun webExchangeBindExceptionHandler(e: WebExchangeBindException): Mono<ErrorResDto> {
        val errors = e.bindingResult.fieldErrors.associate {
            it.field to it.defaultMessage.orEmpty()
        }
        return Mono.just(ErrorResDto("입력값에 오류가 있습니다", null, errors))
    }

}