package com.spacewalk.domain.article

import com.spacewalk.audit.AuditingDomain
import javax.persistence.*

@Entity
class ArticleLike(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: Article

) : AuditingDomain() {

}