package com.spacewalk.security.service

import com.spacewalk.domain.account.Account
import com.spacewalk.domain.account.repository.AccountRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder

) {

    fun authenticate(username: String, password: String): Account {

        val accountOptional = accountRepository.findByUsername(username)
        if (accountOptional.isEmpty) {
            throw UsernameNotFoundException("Account not found with username: $username")
        }

        val account = accountOptional.get()
        if (!passwordEncoder.matches(password, account.password)) {
            throw BadCredentialsException("Invalid password")
        }

        return account;

    }

}