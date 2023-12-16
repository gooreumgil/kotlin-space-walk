package com.spacewalk.domain.account

import javax.persistence.*

@Entity
open class AccountRoleRelation(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    open var account: Account? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    open var role: Role? = null

) {
    fun updateRole(role: Role) {
        this.role = role
        role.addAccount(this)
    }

    fun updateAccount(account: Account) {
        this.account = account
        account.addRole(this)
    }
}