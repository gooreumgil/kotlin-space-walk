package com.spacewalk.domain.user

import com.spacewalk.audit.AuditingDomain
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.article.Article
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
    val email: String,

    @Column(nullable = false)
    var password: String,

    var age: Int,

    @Column(name = "is_deleted")
    var deleted: Boolean = false,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val articleList: MutableList<Article> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val roleList: MutableList<UserRoleRelation> = mutableListOf(),

    ) : AuditingDomain() {

    companion object {
        fun create(dto: UserSaveReqDto): User {
            return User(username = dto.username, age = dto.age, email = dto.email, password = dto.password);
        }
    }

    fun addRole(userRoleRelation: UserRoleRelation) {
        roleList.add(userRoleRelation)
    }

    fun addArticle(article: Article) {
        articleList.add(article)
        article.updateUser(this);
    }


}

