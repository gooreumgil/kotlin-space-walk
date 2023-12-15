package com.spacewalk.domain.article

import com.spacewalk.domain.account.Account
import javax.persistence.*

@Entity
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val author: Account? = null

}