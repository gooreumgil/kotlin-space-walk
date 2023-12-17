package com.spacewalk.security.controller

import com.spacewalk.facade.ReactiveFacade
import com.spacewalk.security.dto.LoginReqDto
import com.spacewalk.security.jwt.JwtTokenProvider
import com.spacewalk.security.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody dto: LoginReqDto): Mono<String> {
        return ReactiveFacade.wrapInMono { authService.authenticate(dto.username, dto.password) }
            .flatMap { user ->
                jwtTokenProvider.createToken(user)
                    .let { authToken -> Mono.just(authToken) }
            }
    }

}