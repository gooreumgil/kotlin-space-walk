package com.spacewalk.security

import com.spacewalk.domain.account.Account
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

data class AccountContext(
    val account: Account,
    val authorities: List<SimpleGrantedAuthority>
) : User(account.username, account.password, authorities)