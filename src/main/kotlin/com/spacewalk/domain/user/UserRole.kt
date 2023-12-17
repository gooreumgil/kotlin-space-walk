package com.spacewalk.domain.user

import com.spacewalk.audit.AuditingDomain
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class UserRole (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val roleName: String,

    @OneToMany(mappedBy = "userRole", cascade = [CascadeType.ALL])
    val userList: MutableList<UserRoleRelation> = mutableListOf()

) : AuditingDomain() {

    companion object {
        fun create(roleName: String): UserRole {
            return UserRole(roleName = roleName)
        }
    }

    fun addUser(userRoleRelation: UserRoleRelation) {
        userList.add(userRoleRelation)
    }
}