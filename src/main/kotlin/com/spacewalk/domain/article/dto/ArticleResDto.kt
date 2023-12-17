package com.spacewalk.domain.article.dto

import com.spacewalk.domain.article.Article
import com.spacewalk.domain.user.User
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

data class ArticleResDto (
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    var user: ArticleUserResDto? = null
) {
    companion object {
        fun fromArticle(article: Article): ArticleResDto {
            return ArticleResDto(id = article.id!!, title = article.title, content = article.content, createdAt = article.createdAt!!, article.lastModifiedAt!!)
        }
    }

    fun updateUser(user: User) {
        this.user = ArticleUserResDto.fromUser(user)
    }

}