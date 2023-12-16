package com.spacewalk.domain.account.repository

import com.spacewalk.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.Optional

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByUsername(username: String) : Optional<Account>

    @Query("select ac from Account ac join fetch ac.roleList ro join fetch ro.role where ac.username = :username")
    fun findByUsernameJoinedRoleList(username: String) : Optional<Account>

}