package com.spacewalk.domain.account.repository

import com.spacewalk.domain.account.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
}