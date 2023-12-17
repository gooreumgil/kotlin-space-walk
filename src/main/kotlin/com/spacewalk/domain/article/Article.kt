package com.spacewalk.domain.article

import com.spacewalk.audit.AuditingDomain
import com.spacewalk.domain.article.dto.ArticleSaveReqDto
import com.spacewalk.domain.article.dto.ArticleUpdateReqDto
import com.spacewalk.domain.user.User
import javax.persistence.*

@Entity
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var content: String,

    @Column(name = "is_deleted")
    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @OneToMany(mappedBy = "article", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val articleLikeList : MutableList<ArticleLike> = mutableListOf()

) : AuditingDomain() {

    companion object {
        fun create(dto: ArticleSaveReqDto): Article {
            return Article(title = dto.title, content = dto.content)
        }
    }

    fun updateUser(user: User) {
        this.user = user
    }

    fun update(dto: ArticleUpdateReqDto) {
        this.title = dto.title
        this.content = dto.content
    }

    fun delete() {
        this.deleted = true
    }

}