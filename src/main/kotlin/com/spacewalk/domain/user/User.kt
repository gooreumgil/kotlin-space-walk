package com.spacewalk.domain.user

import com.spacewalk.audit.AuditingDomain
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.article.Article
import com.spacewalk.domain.order.Order
import javax.persistence.*

@Entity
@Table(name = "users")
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,

    val age: Int,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val orderList: MutableList<Order> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val articleList: MutableList<Article> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val roleList: MutableList<UserRoleRelation> = mutableListOf(),

    ) : AuditingDomain() {

    companion object {
        fun create(dto: UserSaveReqDto): User {
            return User(username = dto.username, password = dto.password, age = dto.age);
        }
    }

    fun addRole(userRoleRelation: UserRoleRelation) {
        roleList.add(userRoleRelation)
    }

}

