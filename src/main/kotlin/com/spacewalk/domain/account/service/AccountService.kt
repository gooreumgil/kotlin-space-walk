package com.spacewalk.domain.account.service

import com.spacewalk.domain.account.Account
import com.spacewalk.domain.account.dto.AccountSaveReqDto
import com.spacewalk.domain.account.repository.AccountRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

@Service
@RequiredArgsConstructor
class AccountService(
    private val accountRepository: AccountRepository,
) {

    @Transactional
    fun createAccount(dto: AccountSaveReqDto): Account {
        return accountRepository.save(Account.create(dto))
    }

    fun findById(id: Long): Optional<Account> {
        return accountRepository.findById(id)
    }

}