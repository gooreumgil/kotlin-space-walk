package com.spacewalk.domain.account.controller

import com.spacewalk.domain.account.Account
import com.spacewalk.domain.account.dto.AccountResDto
import com.spacewalk.domain.account.dto.AccountSaveReqDto
import com.spacewalk.domain.account.service.AccountService
import com.spacewalk.facade.ReactiveFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.Optional

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun create(@RequestBody dto: AccountSaveReqDto): ResponseEntity<AccountResDto> {

        val account = accountService.createAccount(dto)
        return ResponseEntity.ok(AccountResDto.fromAccount(account))

    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Mono<AccountResDto>? {
        val wrapInMono = ReactiveFacade.wrapInMono { accountService.findById(id) }.flux()
        return ReactiveFacade.wrapInMono { accountService.findById(id) }.map { account -> AccountResDto.fromAccount(account.get()) }

    }


}