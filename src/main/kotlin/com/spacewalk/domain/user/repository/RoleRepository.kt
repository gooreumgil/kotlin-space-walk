package com.spacewalk.domain.user.repository

import com.spacewalk.domain.user.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
}