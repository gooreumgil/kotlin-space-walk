package com.spacewalk.domain.account.controller

import com.spacewalk.domain.account.dto.AccountResDto
import com.spacewalk.domain.account.dto.AccountSaveReqDto
import com.spacewalk.domain.account.service.AccountService
import com.spacewalk.facade.ReactiveFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import kotlin.RuntimeException

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun create(@RequestBody dto: AccountSaveReqDto): ResponseEntity<AccountResDto> {

        val account = ReactiveFacade.wrapInMono { accountService.createAccount(dto) }.block() ?: throw RuntimeException("Account create failed")
        return ResponseEntity.ok(AccountResDto.fromAccount(account))

    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<AccountResDto>? {

        val account = ReactiveFacade.wrapInMono { accountService.findById(id) }.block()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with ID: $id")

        return ResponseEntity.ok(AccountResDto.fromAccount(account.get()))

    }


}