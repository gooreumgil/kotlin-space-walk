package com.spacewalk.security

import com.spacewalk.domain.account.Account
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

data class AccountContext(
    val id: Long,
    val username: String,
    val age: Int
)