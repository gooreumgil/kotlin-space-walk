package com.spacewalk.domain.user.service

import com.spacewalk.domain.user.UserRole
import com.spacewalk.domain.user.repository.UserRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserRoleService(private val userRoleRepository: UserRoleRepository) {

}