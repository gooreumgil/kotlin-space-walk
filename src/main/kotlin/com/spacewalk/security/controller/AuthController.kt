package com.spacewalk.security.controller

import com.spacewalk.security.dto.AuthReqDto
import com.spacewalk.security.jwt.JwtTokenProvider
import com.spacewalk.security.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody dto: AuthReqDto): ResponseEntity<String> {

        return authService.authenticate(dto.username, dto.password)
            .let { user ->
                jwtTokenProvider.createToken(user)
            }
            .let { authToken ->
                ResponseEntity.ok(authToken)
            }

    }

}