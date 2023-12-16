package com.spacewalk.init

import com.spacewalk.domain.account.Account
import com.spacewalk.domain.account.AccountRoleRelation
import com.spacewalk.domain.account.Role
import com.spacewalk.domain.account.dto.AccountResDto
import com.spacewalk.domain.account.dto.AccountSaveReqDto
import com.spacewalk.domain.account.repository.AccountRepository
import com.spacewalk.domain.account.repository.RoleRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.time.LocalTime
import java.util.function.Consumer

@Component
class ApplicationInitializer(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun init() {

        val accountRole = roleRepository.save(Role.create("ROLE_ACCOUNT"))
        val accountManager = roleRepository.save(Role.create("ROLE_MANAGER"))
        val accountAdmin = roleRepository.save(Role.create("ROLE_ADMIN"))

        val account1 = accountRepository.save(Account.create(AccountSaveReqDto("account", passwordEncoder.encode("pass"), 20)))
        val account2 = accountRepository.save(Account.create(AccountSaveReqDto("manager", passwordEncoder.encode("pass"), 30)))
        val account3 = accountRepository.save(Account.create(AccountSaveReqDto("admin", passwordEncoder.encode("pass"), 40)))


        val accountRoleRelation1 = AccountRoleRelation()
        accountRoleRelation1.updateAccount(account1)
        accountRoleRelation1.updateRole(accountRole)

        val accountRoleRelation2 = AccountRoleRelation()
        accountRoleRelation2.updateAccount(account2)
        accountRoleRelation2.updateRole(accountManager)

        val accountRoleRelation3 = AccountRoleRelation()
        accountRoleRelation3.updateAccount(account3)
        accountRoleRelation3.updateRole(accountAdmin)


    }


}