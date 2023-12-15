package com.spacewalk.domain.account.repository

import com.spacewalk.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByUsername(username: String) : Optional<Account>

}