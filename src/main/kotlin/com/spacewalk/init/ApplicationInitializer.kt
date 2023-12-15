package com.spacewalk.init

import com.spacewalk.domain.account.Account
import com.spacewalk.domain.account.AccountRoleRelation
import com.spacewalk.domain.account.Role
import com.spacewalk.domain.account.dto.AccountSaveReqDto
import com.spacewalk.domain.account.repository.AccountRepository
import com.spacewalk.domain.account.repository.RoleRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ApplicationInitializer(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository
) {

    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun init() {

        val account1 = accountRepository.save(Account.create(AccountSaveReqDto("account", "pass", 20)))
        val account2 = accountRepository.save(Account.create(AccountSaveReqDto("manager", "pass", 30)))
        val account3 = accountRepository.save(Account.create(AccountSaveReqDto("admin", "pass", 40)))

        val accountRole = roleRepository.save(Role.create("ROLE_ACCOUNT"))
        val accountManager = roleRepository.save(Role.create("ROLE_MANAGER"))
        val accountAdmin = roleRepository.save(Role.create("ROLE_ADMIN"))

        val accountRoleRelation1 = AccountRoleRelation()
        accountRoleRelation1.setAccount(account1)
        accountRoleRelation1.setRole(accountRole)

        val accountRoleRelation2 = AccountRoleRelation()
        accountRoleRelation2.setAccount(account2)
        accountRoleRelation2.setRole(accountManager)

        val accountRoleRelation3 = AccountRoleRelation()
        accountRoleRelation3.setAccount(account3)
        accountRoleRelation3.setRole(accountAdmin)

    }

}