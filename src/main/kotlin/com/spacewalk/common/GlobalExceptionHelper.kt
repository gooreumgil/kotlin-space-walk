package com.spacewalk.common

import com.spacewalk.exception.HttpException
import com.spacewalk.exception.HttpExceptionCode
import org.springframework.http.HttpStatus

class GlobalExceptionHelper {

    companion object {
        @JvmStatic
        fun createNotFoundException(entityName: String, entityId: Long): HttpException {
            return HttpException(
                status = HttpStatus.BAD_REQUEST,
                HttpExceptionCode.NOT_FOUND,
                message = "존재하지 않는 $entityName 입니다. ${entityName}Id = $entityId"
            )
        }
    }

}