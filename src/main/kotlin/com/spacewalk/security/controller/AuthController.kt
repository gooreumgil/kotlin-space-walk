package com.spacewalk.security.controller

import com.spacewalk.security.dto.AuthReqDto
import com.spacewalk.security.jwt.JwtTokenProvider
import com.spacewalk.security.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authService: AuthService
) {

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody dto: AuthReqDto): ResponseEntity<String> {

        val account = authService.authenticate(dto.username, dto.password)
        val authToken = jwtTokenProvider.createToken(account)

        return ResponseEntity.ok(authToken)

    }

}