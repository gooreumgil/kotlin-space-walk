package com.spacewalk.domain.article

import com.spacewalk.audit.AuditingDomain
import com.spacewalk.domain.user.User
import javax.persistence.*

@Entity
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String
) : AuditingDomain() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null

}