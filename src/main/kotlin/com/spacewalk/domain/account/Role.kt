package com.spacewalk.domain.account

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Role (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val roleName: String,

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL])
    val accountList: MutableList<AccountRoleRelation> = mutableListOf()

) {

    companion object {
        fun create(roleName: String): Role {
            return Role(roleName = roleName)
        }
    }

    fun addAccount(accountRoleRelation: AccountRoleRelation) {
        accountList.add(accountRoleRelation)
    }
}