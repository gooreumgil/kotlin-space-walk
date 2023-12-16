package com.spacewalk.domain.user.repository

import com.spacewalk.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String) : Optional<User>

    @Query("select us from User us join fetch us.roleList ro join fetch ro.role where us.id = :id")
    fun findByIdJoinedRoleList(id: Long) : Optional<User>

    @Query("select us from User us join fetch us.roleList ro join fetch ro.role where us.username = :username")
    fun findByUsernameJoinedRoleList(username: String) : Optional<User>

}