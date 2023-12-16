package com.spacewalk.init

import com.spacewalk.domain.user.User
import com.spacewalk.domain.user.UserRoleRelation
import com.spacewalk.domain.user.Role
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.user.repository.UserRepository
import com.spacewalk.domain.user.repository.RoleRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ApplicationInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun init() {

        val userRole = roleRepository.save(Role.create("ROLE_USER"))
        val userManager = roleRepository.save(Role.create("ROLE_MANAGER"))
        val userAdmin = roleRepository.save(Role.create("ROLE_ADMIN"))

        val user1 = userRepository.save(User.create(UserSaveReqDto("user", passwordEncoder.encode("pass"), 20)))
        val user2 = userRepository.save(User.create(UserSaveReqDto("manager", passwordEncoder.encode("pass"), 30)))
        val user3 = userRepository.save(User.create(UserSaveReqDto("admin", passwordEncoder.encode("pass"), 40)))


        val userRoleRelation1 = UserRoleRelation()
        userRoleRelation1.updateUser(user1)
        userRoleRelation1.updateRole(userRole)

        val userRoleRelation2 = UserRoleRelation()
        userRoleRelation2.updateUser(user2)
        userRoleRelation2.updateRole(userManager)

        val userRoleRelation3 = UserRoleRelation()
        userRoleRelation3.updateUser(user3)
        userRoleRelation3.updateRole(userAdmin)

    }


}