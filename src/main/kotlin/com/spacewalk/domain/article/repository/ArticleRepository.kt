package com.spacewalk.domain.article.repository

import com.spacewalk.domain.article.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
}