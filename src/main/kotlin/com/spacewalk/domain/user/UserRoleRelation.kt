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
    @JoinColumn(name = "user_role_id")
    open var userRole: UserRole? = null

) : AuditingDomain() {
    fun updateUserRole(userRole: UserRole) {
        this.userRole = userRole
        userRole.addUser(this)
    }

    fun updateUser(user: User) {
        this.user = user
        user.addRole(this)
    }
}