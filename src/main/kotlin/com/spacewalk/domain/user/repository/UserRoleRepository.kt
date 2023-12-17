package com.spacewalk.domain.user.repository

import com.spacewalk.domain.user.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRoleRepository : JpaRepository<UserRole, Long> {

    fun findByRoleName(roleName: String) : Optional<UserRole>

}