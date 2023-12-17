package com.spacewalk.domain.article.dto

import com.spacewalk.domain.article.Article
import com.spacewalk.domain.user.User

data class ArticleResDto (
    val id: Long,
    val title: String,
    val content: String,
    var user: ArticleUserResDto? = null
) {
    companion object {
        fun fromArticle(article: Article): ArticleResDto {
            return ArticleResDto(id = article.id!!, title = article.title, content = article.content)
        }
    }

    fun updateUser(user: User) {
        this.user = ArticleUserResDto.fromUser(user)
    }

}