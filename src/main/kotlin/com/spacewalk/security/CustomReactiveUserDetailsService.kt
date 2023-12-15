package com.spacewalk.security

import com.spacewalk.domain.account.repository.AccountRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Component
@Transactional
class CustomReactiveUserDetailsService(private val accountRepository: AccountRepository) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {

        val accountOptional = accountRepository.findByUsername(username!!)
        if (accountOptional.isEmpty) {
            throw UsernameNotFoundException("Account not found with username: $username")
        }

        val account = accountOptional.get()
        val roleList = account.roleList

        val userRoles = roleList
            .map { it.role!!.roleName }
            .toSet()

        val authorities = userRoles.map { SimpleGrantedAuthority(it) }

        return Mono.just(AccountContext(account, authorities))

    }
}