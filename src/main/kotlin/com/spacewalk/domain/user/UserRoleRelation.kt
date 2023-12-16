package com.spacewalk.domain.user

import com.spacewalk.audit.AuditingDomain
import javax.persistence.*

@Entity
open class UserRoleRelation(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    open var role: Role? = null

) : AuditingDomain() {
    fun updateRole(role: Role) {
        this.role = role
        role.addUser(this)
    }

    fun updateUser(user: User) {
        this.user = user
        user.addRole(this)
    }
}