package com.spacewalk.domain.account

import com.spacewalk.domain.account.dto.AccountSaveReqDto
import com.spacewalk.domain.article.Article
import javax.persistence.*

@Entity
class Account (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,

    val age: Int,

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL])
    val articleList: MutableList<Article> = mutableListOf(),

    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    val roleList: MutableList<AccountRoleRelation> = mutableListOf()

) {

    companion object {
        fun create(dto: AccountSaveReqDto): Account {
            return Account(username = dto.username, password = dto.password, age = dto.age);
        }
    }

    fun addRole(accountRoleRelation: AccountRoleRelation) {
        roleList.add(accountRoleRelation)
    }



}

